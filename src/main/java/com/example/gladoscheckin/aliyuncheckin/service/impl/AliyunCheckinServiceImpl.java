package com.example.gladoscheckin.aliyuncheckin.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.gladoscheckin.aliyuncheckin.pojo.AliyunCheckin;
import com.example.gladoscheckin.aliyuncheckin.mapper.AliyunCheckinMapper;
import com.example.gladoscheckin.aliyuncheckin.pojo.AliyunCheckinVO;
import com.example.gladoscheckin.aliyuncheckin.service.AliyunCheckinService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.common.SendWeChat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hhy
 * @since 2023-10-27
 */
@Service
@Slf4j
public class AliyunCheckinServiceImpl extends ServiceImpl<AliyunCheckinMapper, AliyunCheckin> implements AliyunCheckinService {

    @Autowired
    private SendWeChat sendWeChat;
    @Override
    public AjaxResult aliyunCheckin() {
        //查询所有签到人员
        List<AliyunCheckin> aliyunCheckins = baseMapper.selectList(new QueryWrapper<>());
        String url = "https://auth.aliyundrive.com/v2/account/token";
        aliyunCheckins.stream().forEach(e -> {

            AliyunCheckinVO aliyunCheckinVO = getAccessToken("refresh_token", e.getAliyunToken());

            if (!ObjectUtils.isEmpty(aliyunCheckinVO)) {
                //签到
                aliyunCheckinVO = checkin(aliyunCheckinVO);
            }
            if(aliyunCheckinVO.getResult()){
                //获取成功，发送通知
                //发送通知
                String emailHeader = "阿里云盘签到通知";
                String emailMessage = "恭喜您阿里云盘签到成功：\n" +
                        "用户："+ aliyunCheckinVO.getName() + "\n" +
                        "签到：" + "本月已签到" + aliyunCheckinVO.getSignInCount() + "次" + "\n" +
                        "奖励：" + aliyunCheckinVO.getAwardNotice() + "\n" +
                        "任务：" + aliyunCheckinVO.getTasknotice();
                try {
                    log.info(emailMessage);
                    sendWeChat.sendMessage("阿里云签到 "+ e.getName() + " " + null, null, e.getPushPlusToken(), emailHeader, emailMessage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        return AjaxResult.build2Success(true);
    }

    /** 获取access_token */
    private AliyunCheckinVO getAccessToken(String grantType, String refreshToken) {

        String url = "https://auth.aliyundrive.com/v2/account/token";
        JSONObject param = new JSONObject();
        param.set("grant_type", grantType);
        param.set("refresh_token", refreshToken);
        String resultStr = HttpRequest.post(url)
                .body(param.toString())
                .timeout(10000)
                .execute().body();
        JSONObject ress = JSONUtil.parseObj(resultStr);
        if (!ObjectUtils.isEmpty(ress.get("code"))) {
            return null;
        }
        String nickName = (String) ress.get("nick_name");
        String userName = (String) ress.get("user_name");
        String name = StrUtil.isEmpty(nickName) ? userName : nickName;
        String accessToken = (String) ress.get("access_token");
        AliyunCheckinVO aliyunCheckinVO = new AliyunCheckinVO();
        aliyunCheckinVO.setName(name);
        aliyunCheckinVO.setAccessToken(accessToken);
        return aliyunCheckinVO;
    }

    /** 执行签到 */
    private AliyunCheckinVO checkin(AliyunCheckinVO aliyunCheckinVO) {
        String url = "https://member.aliyundrive.com/v1/activity/sign_in_list";
        JSONObject param = new JSONObject();
        param.set("isReward", false);
        String resultStr = HttpRequest.post(url)
                .header("Authorization", "Bearer " + aliyunCheckinVO.getAccessToken())
                .body(param.toString())
                .timeout(10000)
                .execute().body();
        JSONObject ress = JSONUtil.parseObj(resultStr);
        if ((Boolean) ress.get("success") == true) {
            JSONObject ress1 = (JSONObject) ress.get("result");
            int signInCount = (Integer) ress1.get("signInCount");
            aliyunCheckinVO.setSignInCount(signInCount);
            aliyunCheckinVO = getReward(aliyunCheckinVO);
        }
        return aliyunCheckinVO;
    }

    /** 获得奖励 */
    private AliyunCheckinVO getReward(AliyunCheckinVO aliyunCheckinVO) {
        String url = "https://member.aliyundrive.com/v1/activity/sign_in_reward";
        JSONObject param = new JSONObject();
        param.set("signInDay", aliyunCheckinVO.getSignInCount());
        String resultStr = HttpRequest.post(url)
                .header("Authorization", "Bearer " + aliyunCheckinVO.getAccessToken())
                .body(param.toString())
                .timeout(10000)
                .execute().body();
        JSONObject ress = JSONUtil.parseObj(resultStr);

        if(!ObjectUtils.isEmpty(ress.get("result")) && (Boolean) ress.get("success")){
            aliyunCheckinVO = getTask(aliyunCheckinVO);
        }

        return aliyunCheckinVO;
    }

    /** 获取今日奖励/任务 */
    private AliyunCheckinVO getTask(AliyunCheckinVO aliyunCheckinVO){
        String url = "https://member.aliyundrive.com/v2/activity/sign_in_list";
        JSONObject param = new JSONObject();
        String resultStr = HttpRequest.post(url)
                .header("Authorization", "Bearer " + aliyunCheckinVO.getAccessToken())
                .body(param.toString())
                .timeout(10000)
                .execute().body();
        JSONObject ress = JSONUtil.parseObj(resultStr);

        if(!ObjectUtils.isEmpty(ress.get("result")) && (Boolean) ress.get("success")){
            Boolean result = (Boolean) ress.get("success");
            JSONObject ress1 = (JSONObject) ress.get("result");
            JSONArray resList = JSONUtil.parseArray(ress1.get("signInInfos"));
            //获取今天是这个月的第几天
            int day = dayOfMonthExample();
            JSONObject ress2 = (JSONObject) resList.get(day - 1);
            JSONArray resList1 = JSONUtil.parseArray(ress2.get("rewards"));
            aliyunCheckinVO.setResult(result);
            String awardNotice = null;
            String tasknotice = null;
            for (int i = 0; i < resList1.size(); i++) {
                JSONObject jsonObject = resList1.getJSONObject(i);
                if("dailySignIn".equals(jsonObject.get("type"))){
                    awardNotice = (String) jsonObject.get("name");
                }
                if("dailyTask".equals(jsonObject.get("type"))){
                    tasknotice = (String) jsonObject.get("remind") + awardNotice;
                }
            }
            aliyunCheckinVO.setAwardNotice(awardNotice);
            aliyunCheckinVO.setTasknotice(tasknotice);
        }
        return aliyunCheckinVO;
    }

    private int dayOfMonthExample(){
        LocalDate today = LocalDate.now();
        int dayOfMonth = today.getDayOfMonth();
        return dayOfMonth;
    }
//    public static void main(String[] args) {
//        AliyunCheckinVO aliyunCheckinVO = getAccessToken("refresh_token", "fa6b448352644ea88389a256a23e6ee4");
//        if (!ObjectUtils.isEmpty(aliyunCheckinVO)) {
//            //签到
//            aliyunCheckinVO = checkin(aliyunCheckinVO);
//        }
//        dayOfMonthExample();
//    }


}
