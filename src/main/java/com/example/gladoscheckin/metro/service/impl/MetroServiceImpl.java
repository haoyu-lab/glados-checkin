package com.example.gladoscheckin.metro.service.impl;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.common.SendWeChat;
import com.example.gladoscheckin.common.Status;
import com.example.gladoscheckin.metro.MetroVO;
import com.example.gladoscheckin.metro.Metror;
import com.example.gladoscheckin.metro.mapper.MetrorMapper;
import com.example.gladoscheckin.metro.service.AESUtil;
import com.example.gladoscheckin.metro.service.MetroService;
import com.example.gladoscheckin.metro.service.TaskUtils;
import com.example.gladoscheckin.pushsend.pojo.VICode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

@Service
@Slf4j
public class MetroServiceImpl extends ServiceImpl<MetrorMapper, Metror> implements MetroService {

    @Autowired
    TaskUtils taskUtils;
    @Autowired
    @Qualifier("asyncTaskExecutor")
    private ThreadPoolTaskExecutor asyncTaskExecutor;
    @Autowired
    private SendWeChat sendWeChat;


    @Override
    public AjaxResult metroCheckin() {
        //查询数据
        QueryWrapper<Metror> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Metror::getIsVaild,"Y");
        List<Metror> metrors = baseMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(metrors)){
            return AjaxResult.build(Status.SERVER_ERROR,"无预约用户","无预约用户");
        }

        /** 检查今天是否需要抢票 */
        Boolean isReservation = true;
        isReservation = taskUtils.checkTomorrowIsHoliday(isReservation);
        if(isReservation){
//            List<FutureTask<List<Void>>> fTaskes = new ArrayList<>(index);
            metrors.forEach(e ->{
                CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
                    taskUtils.start(e);
                },asyncTaskExecutor);
            });

        }
        return AjaxResult.build2Success(true);
    }

    @Override
    public AjaxResult searchMetro() {
        QueryWrapper<Metror> queryWrapper = new QueryWrapper<>();
        List<Metror> metrors = baseMapper.selectList(queryWrapper);
        return AjaxResult.build2Success(metrors);
    }

    @Override
    public void refreshIsNeedOrder(){
        QueryWrapper<Metror> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Metror::getIsVaild,"Y");
        List<Metror> metrors = baseMapper.selectList(queryWrapper);
        metrors.forEach(e ->{
            try {
                Boolean aBoolean = taskUtils.checkIsMetro(e);
                if(aBoolean){
                    e.setIsNeedOrder("true");
                    baseMapper.updateById(e);
                }else{
                    e.setIsNeedOrder("false");
                    baseMapper.updateById(e);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

        });
    }

    @Override
    public void initializeIsNeedOrder(){
        QueryWrapper<Metror> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Metror::getIsVaild,"Y");
        List<Metror> metrors = baseMapper.selectList(queryWrapper);
        metrors.forEach(e -> {
            e.setIsNeedOrder("false");
        });
        this.saveOrUpdateBatch(metrors);
    }

    @Override
    public void updateMetror(Metror metror) {
        this.saveOrUpdate(metror);
    }

    @Override
    public AjaxResult getVlCode(String phone) {
        if(StringUtils.isEmpty(phone)){
            return AjaxResult.build(Status.SERVER_ERROR,"请输入手机号","请输入手机号");
        }
        QueryWrapper<Metror> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(Metror::getPhone,phone);
        List<Metror> metrors = baseMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(metrors)){
            return AjaxResult.build2ServerError("该用户未注册，请联系管理员注册");
        }
        long time = new Date().getTime();
        //https://webapi.mybti.cn/User/SendVerifyCode?phoneNumber=
        String sha1 = AESUtil.getSha1(String.valueOf(time));
        sha1 = AESUtil.getSha1(String.valueOf(time)+sha1);
        String resultStrs = HttpRequest.get("https://webapi.mybti.cn/User/SendVerifyCode?phoneNumber="+phone+"&clientid=7e80a759-5bf3-4504-bfab-71572b025005&ts="+time+"&sign="+sha1)
                .header(Header.CONTENT_TYPE, "application/json;charset=UTF-8")
//                .header(Header.AUTHORIZATION, metror.getMetroToken())
                .header("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1")
                .timeout(10000)
                .execute().body();

        return AjaxResult.build2Success(true);
    }

    @Override
    public AjaxResult metorLogin(VICode viCode){
        if(StringUtils.isEmpty(viCode.getPhone())){
            return AjaxResult.build(Status.SERVER_ERROR,"请输入手机号","请输入手机号");
        }
        if(StringUtils.isEmpty(viCode.getVerifyCode())){
            return AjaxResult.build(Status.SERVER_ERROR,"请输入手机号","请输入手机号");
        }
        JSONObject param = new JSONObject();
        param.set("clientId", "7e80a759-5bf3-4504-bfab-71572b025005");
        param.set("openId", "");
        param.set("phoneNumber", viCode.getPhone());
        param.set("verifyCode", viCode.getVerifyCode());
        String resultStr = HttpRequest.post("https://webapi.mybti.cn/User/SignUp")
//                .header(Header.AUTHORIZATION, metror.getMetroToken())
                .header(Header.CONTENT_TYPE, "application/json;charset=UTF-8")
                .header("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1")
                .body(param.toString())
                .timeout(10000)
                .execute().body();
        if(!StringUtils.isEmpty(resultStr) && resultStr.contains("{\"userInfo\"")){
            com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSONObject.parseObject(resultStr);
            String token = (String)object.get("accesstoken");
            QueryWrapper<Metror> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().eq(Metror::getPhone,viCode.getPhone());
            List<Metror> metrors = baseMapper.selectList(queryWrapper);
            if(CollectionUtils.isEmpty(metrors)){
                return AjaxResult.build2ServerError("该用户未注册，请联系管理员注册");
            }else {
                Metror metror = metrors.get(0);
                metror.setMetroToken(token);
                baseMapper.updateById(metror);

                try {
                    //发送通知
                    String emailHeader = "地铁预约授权刷新成功";
                    String decode = new String(Base64.getDecoder().decode(token),"UTF-8");
                    String[] split = decode.split(",");
                    String time = split[1];
                    Date date = new Date(Long.parseLong(time)-84600000L);//取到期日前一天
                    //取到时间
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                    String format = simpleDateFormat.format(date);
                    String emailMessage = "恭喜您地铁预约授权成功，下次需授权时间为："+format;
                    log.info("{}: 恭喜您地铁预约授权成功\n 下次需授权时间为：{}", metror.getName(),format);
                    sendWeChat.sendMessage(metror.getName(), null, metror.getPushPlusToken(), emailHeader, emailMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return AjaxResult.build2Success("token刷新成功");
        }
        return AjaxResult.build2ServerError("token刷新失败");
    }

    //该方法为测试多线程方法，不可用
    @Override
    public AjaxResult metroCheckin1() {
        //查询数据
        QueryWrapper<Metror> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Metror::getIsVaild,"Y");
        List<Metror> metrors = baseMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(metrors)){
            return AjaxResult.build(Status.SERVER_ERROR,"无预约用户","无预约用户");
        }

        /** 检查今天是否需要抢票 */
        Boolean isReservation = true;
        isReservation = taskUtils.checkTomorrowIsHoliday(isReservation);
        if(isReservation){
//            metrors.forEach(e ->{
//                new Thread(()->{
//                    log.info("用户：{}",e.getName());
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException interruptedException) {
//                        interruptedException.printStackTrace();
//                    }
//                }).start();
//            });

//            List<FutureTask<List<Void>>> fTaskes = new ArrayList<>(index);
            metrors.forEach(e ->{
                CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
                    log.info("用户：{}",e.getName());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
//                    taskUtils.start(e);
                },asyncTaskExecutor);
            });

        }
        return AjaxResult.build2Success(true);
    }

}
