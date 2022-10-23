package com.example.gladoscheckin.metro.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.gladoscheckin.metro.MetroVO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskUtils {

    /**
     * 检查今天是否需要抢票
     */
    public void checkTomorrowIsHoliday(MetroVO metroVO){
        String res = HttpUtil.get("https://tool.bitefu.net/jiari/?d=" + DateUtil.tomorrow().toString("yyyyMMdd"));
        System.out.println("检查一下明天是不是假期: " + res);
        if ("0".equals(res)){
            System.out.println("嘤嘤嘤明天要上班，还是需要抢票滴！！");
            metroVO.setIsReservation(true);
        }else{
            System.out.println("明个放假，不用抢票啦！！");
            metroVO.setIsReservation(false);
        }
    }

    /**
     * 检查token是否过期
     */
    public Boolean checkToken(MetroVO metroVO){
        String aToken = Base64.decodeStr(metroVO.getAuthorization());
        String[] aTokens = aToken.split(",");
        DateTime tokenTime = new DateTime(Long.parseLong(aTokens[1]));
        LocalDateTime tokenRxpireTime = LocalDateTimeUtil.of(tokenTime);
        DateTime dateTime = new DateTime(DateUtil.tomorrow().toString("yyyy-MM-dd") + " 23:59:59", DatePattern.NORM_DATETIME_FORMAT);
        LocalDateTime reservationTime = LocalDateTimeUtil.of(dateTime);
        DateTime newTime = new DateTime(DateUtil.date().toString("yyyy-MM-dd HH:mm:ss"),DatePattern.NORM_DATETIME_FORMAT);
        LocalDateTime startTime = LocalDateTimeUtil.of(newTime);
        if(tokenRxpireTime.isBefore(startTime)){
            System.out.println("您的token已过期，请尽快修改！");
            /** 此处需添加微信通知 */
            return false;
        }else if (tokenRxpireTime.isBefore(reservationTime)){
            System.out.println("您的token将在一天后过期，请尽快修改！");
//            MailUtils.sendMail(email, "您的token将在一天后过期，请尽快修改！");
            /** 此处需添加微信通知 */

            return true;
        }else {
            System.out.println("token检查完成，未过期！");

            return true;
        }
//        System.out.println("Token过期时间：" + tokenRxpireTime);
    }

    public void startReservation(MetroVO metroVO){
        if (!metroVO.getIsReservation()) {
            return;
        }

        boolean flag = false;
        int count = 0;

        JSONObject param = new JSONObject();
        param.set("lineName", "昌平线");
        param.set("snapshotWeekOffset", 0);
        param.set("stationName", "沙河站");
        param.set("enterDate", DateUtil.tomorrow().toString("yyyyMMdd"));
        param.set("snapshotTimeSlot", "0630-0930");
        param.set("timeSlot", metroVO.getTime());

        System.out.println("地铁预约参数组装完成"+ param);

        while (count < 5 && !flag){
            System.out.println(LocalDateTime.now() + ": 第"+(count+1)+"次请求预约接口");
            String resultStr = HttpRequest.post("https://webapi.mybti.cn/Appointment/CreateAppointment")
                    .header(Header.AUTHORIZATION, metroVO.getAuthorization())
                    .header(Header.CONTENT_TYPE, "application/json;charset=UTF-8")
                    .header("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1")
                    .body(param.toString())
                    .timeout(10000)
                    .execute().body();
            System.out.println(LocalDateTime.now() + ": 第"+(count+1)+"次预约结果返回值为："+resultStr);
            if (resultStr != null){
                JSONObject res;
                try {
                    res = JSONUtil.parseObj(resultStr);
                    if (null != res.get("balance")){
                        if ((Integer)res.get("balance") > 0){
                            System.out.println(LocalDateTime.now() + ": 恭喜您第"+(count+1)+"次预约成功，明天不用排队啦！");
                            flag = true;
                        }
                    }else{
                        System.out.println(LocalDateTime.now() + ": 第"+(count+1)+"次预约失败");
                    }
                } catch (Exception e) {
                    System.out.println(LocalDateTime.now() + ": 第"+(count+1)+"次预约失败；" + "原因：异常【" + e.getMessage() + "】");
                }
            }else{
                System.out.println(LocalDateTime.now() + ": 第"+(count+1)+"次预约失败");
            }
            count++;
        }

        /** 改为微信通知 */
        if (flag){
//            MailUtils.sendResMail(email, "预约成功！","");
        }else{
//            MailUtils.sendResMail(email, "预约失败！","");
        }

        System.out.println(LocalDateTime.now() + ": 定时任务执行完成");
    }

    public static void main(String[] args) {
        String aToken = Base64.decodeStr("YTliYmQzNDQtMzE5Zi00NjcxLTgyYTMtZmQwNzY5YzA5ZTk2LDE2NjcyMDk2NzE1MDMsQ2QvcnlMYVZaZS8yQ0VQY2NlaFZ1NGM0U2MwPQ==");
        String[] aTokens = aToken.split(",");
        DateTime tokenTime = new DateTime(Long.parseLong(aTokens[1]));
        LocalDateTime tokenRxpireTime = LocalDateTimeUtil.of(tokenTime);
        DateTime dateTime = new DateTime(DateUtil.tomorrow().toString("yyyy-MM-dd") + " 23:59:59", DatePattern.NORM_DATETIME_FORMAT);
        LocalDateTime reservationTime = LocalDateTimeUtil.of(dateTime);
        DateTime newTime = new DateTime(DateUtil.date().toString("yyyy-MM-dd HH:mm:ss"),DatePattern.NORM_DATETIME_FORMAT);
        LocalDateTime startTime = LocalDateTimeUtil.of(newTime);
        if(tokenRxpireTime.isBefore(startTime)){
            System.out.println("您的token已过期，请尽快修改！");
        }else if (tokenRxpireTime.isBefore(reservationTime)){
            System.out.println("您的token将在一天后过期，请尽快修改！");
//            MailUtils.sendMail(email, "您的token将在一天后过期，请尽快修改！");
            /** 此处需添加微信通知 */
        }else {
        System.out.println("token检查完成，未过期！");
//            return true;
        }
        System.out.println("Token过期时间：" + tokenRxpireTime);
    }
}
