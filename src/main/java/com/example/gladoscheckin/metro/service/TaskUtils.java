package com.example.gladoscheckin.metro.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.gladoscheckin.common.SendWeChat;
import com.example.gladoscheckin.common.TimeRangeConverter;
import com.example.gladoscheckin.metro.Metror;
import com.example.gladoscheckin.metro.mapper.MetrorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Component
@Slf4j
public class TaskUtils {

    @Autowired
    private SendWeChat sendWeChat;

    @Autowired
    private MetroService metroService;


    /**
     * 检查今天是否需要抢票
     */
    public Boolean checkTomorrowIsHoliday() {
        try{
            String resultStr = HttpRequest.get("https://api.apihubs.cn/holiday/get?api_key=fee0ef68057340492d19ebb1e5061ae3499f&field=workday&date="+DateUtil.tomorrow().toString("yyyyMMdd")+"&cn=1")
                    .header("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1")
                    .timeout(3000)
                    .execute().body();
            log.info(resultStr);
            JSONObject res = JSONUtil.parseObj(resultStr);
            JSONObject ress = res.getJSONObject("data");
            JSONArray reArray = ress.getJSONArray("list");
            JSONObject ress1 = reArray.getJSONObject(0);
            int response = (Integer) ress1.get("workday");
            if(response == 1){
                //说明是工作日
                log.info("明天要上班，还是需要抢票滴！！");
                return true;
            }
            if(response == 2){
                //说明是休息日
                log.info("明个放假，不用抢票啦！！");
                return false;
            }
        }catch (Exception e){
            //第一种方法调用失败
            log.info("第一种方法调用失败");
            e.printStackTrace();
        }
        try{
            String res = HttpUtil.get("https://tool.bitefu.net/jiari/?d=" + DateUtil.tomorrow().toString("yyyyMMdd"),2000);
            log.info("检查一下明天是不是假期{}", res);
            if ("0".equals(res)) {
                log.info("明天要上班，还是需要抢票滴！！");
                return true;
            } else {
                log.info("明个放假，不用抢票啦！！");
                return false;
            }
        }catch (Exception e){
            //第二种方法调用失败
            log.info("第二种方法调用失败");
            e.printStackTrace();
        }


        return false;
    }

    /**
     * 检查今天是否需要抢票
     */
    public Boolean checkTodayIsHoliday() {
        try{
            String resultStr = HttpRequest.get("https://api.apihubs.cn/holiday/get?api_key=fee0ef68057340492d19ebb1e5061ae3499f&field=workday&date="+DateUtil.date().toString("yyyyMMdd")+"&cn=1")
                    .header("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1")
                    .timeout(3000)
                    .execute().body();
            log.info(resultStr);
            JSONObject res = JSONUtil.parseObj(resultStr);
            JSONObject ress = res.getJSONObject("data");
            JSONArray reArray = ress.getJSONArray("list");
            JSONObject ress1 = reArray.getJSONObject(0);
            int response = (Integer) ress1.get("workday");
            if(response == 1){
                //说明是工作日
                log.info("今天要上班，还是需要进站抢票滴！！");
                return true;
            }
            if(response == 2){
                //说明是休息日
                log.info("今天放假，不用进站不用抢票啦！！");
                return false;
            }
        }catch (Exception e){
            //第一种方法调用失败
            log.info("第一种方法调用失败");
            e.printStackTrace();
        }
        try{
            String res = HttpUtil.get("https://tool.bitefu.net/jiari/?d=" + DateUtil.date().toString("yyyyMMdd"),2000);
            log.info("检查一下今天是不是假期{}", res);
            if ("0".equals(res)) {
                log.info("今天要上班，还是需要进站抢票滴！！");
                return true;
            } else {
                log.info("今天放假，不用进站不用抢票啦！！");
                return false;
            }
        }catch (Exception e){
            //第二种方法调用失败
            log.info("第二种方法调用失败");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 检查token是否过期
     */
    public Boolean checkToken(Metror metror) throws Exception {
        String aToken = Base64.decodeStr(metror.getMetroToken());
        String[] aTokens = aToken.split(",");
        DateTime tokenTime = new DateTime(Long.parseLong(aTokens[1]));
        LocalDateTime tokenRxpireTime = LocalDateTimeUtil.of(tokenTime);
        tokenRxpireTime = tokenRxpireTime.plusHours(-8L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = simpleDateFormat.format(new Date(Long.parseLong(aTokens[1])));

        DateTime dateTime = new DateTime(DateUtil.tomorrow().toString("yyyy-MM-dd") + " "+time, DatePattern.NORM_DATETIME_FORMAT);
        LocalDateTime reservationTime = LocalDateTimeUtil.of(dateTime);
        DateTime newTime = new DateTime(DateUtil.date().toString("yyyy-MM-dd HH:mm:ss"), DatePattern.NORM_DATETIME_FORMAT);
        LocalDateTime startTime = LocalDateTimeUtil.of(newTime);
        String expiredTime = LocalDateTimeToDateConversion(tokenRxpireTime);
        log.info("{},到期时间：{}",metror.getName() + " " + metror.getPhone(),expiredTime);
        if (tokenRxpireTime.isBefore(startTime)) {
            log.info("{}：您的授权已过期，无法进行预约！请尽快前往： https://www.huyoa.com/subway  登录授权！", metror.getName() + " " + metror.getPhone());
            if(!StringUtils.isEmpty(metror.getTokenFlag()) && "N".equals(metror.getTokenFlag())){
                return false;
            }
            String emailMessage = "您的授权已过期，无法进行预约！请尽快前往： <a href=\"https://www.huyoa.com/subway\">https://www.huyoa.com/subway</a>  登录授权！";
            String emailHeader = "地铁预约服务授权到期提醒！！！";
            /** 此处需添加微信通知 */
            sendWeChat.sendMessage(metror.getName(), null, metror.getPushPlusToken(), emailHeader, emailMessage);

            return false;
        } else if (tokenRxpireTime.isBefore(reservationTime)) {
            log.info("{}：您的token即将过期，请尽快前往： https://www.huyoa.com/subway  登录授权！", metror.getName() + " " + metror.getPhone());
            String emailMessage = "您的授权即将过期，过期时间为："+ expiredTime +"  过期后将无法自动预约！请尽快前往： <a href=\"https://www.huyoa.com/subway\">https://www.huyoa.com/subway</a>  登录授权！";
            String emailHeader = "地铁预约服务授权到期提醒！！";
//            MailUtils.sendMail(email, "您的token将在一天后过期，请尽快修改！");
            /** 此处需添加微信通知 */
            sendWeChat.sendMessage(metror.getName(), null, metror.getPushPlusToken(), emailHeader, emailMessage);

            return true;
        } else {
            log.info("{}：token检查完成，未过期！", metror.getName() + " " + metror.getPhone());

            return true;
        }
    }

    public void startReservation(Metror metror) throws Exception {

        //查询是否有预约记录，有则不预约 （20221025，改成定时任务）
//        Boolean aBoolean = checkIsMetro(metror);

        if ("true".equals(metror.getIsNeedOrder())) {
            log.info("{}：已预约，不可重复预约", metror.getName() + " " + metror.getPhone());
        }

        if ("false".equals(metror.getIsNeedOrder())) {
            //无预约记录
            String appointMentId = "";
            boolean flag = false;
            int count = 0;

            JSONObject param = new JSONObject();
            param.set("lineName", metror.getLineName());
            param.set("snapshotWeekOffset", 0);
            param.set("stationName", metror.getStationName());
            param.set("enterDate", DateUtil.tomorrow().toString("yyyyMMdd"));
            param.set("snapshotTimeSlot", "0630-0930");
            if(!StringUtils.isEmpty(metror.getWeekDate())){
                param.set("timeSlot", "0750-0800");
            }else{
                param.set("timeSlot", metror.getMetroTime());
            }


            log.info("{}：地铁预约参数组装完成", metror.getName() + " " + metror.getPhone());

            String emailMessage = "";
            while (count < 5 && !flag) {
                try{
                    log.info("{} : 第" + (count + 1) + "次请求预约接口", metror.getName() + " " + metror.getPhone());
                    String resultStr = HttpRequest.post("https://webapi.mybti.cn/Appointment/CreateAppointment")
                            .header(Header.AUTHORIZATION, metror.getMetroToken())
                            .header(Header.CONTENT_TYPE, "application/json;charset=UTF-8")
                            .header("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1")
                            .body(param.toString())
                            .timeout(10000)
                            .execute().body();

                    log.info("{}: 第" + (count + 1) + "次预约结果返回值为：" + resultStr, metror.getName() + " " + metror.getPhone());
                    if (resultStr != null) {
                        JSONObject res;
                        try {
                            res = JSONUtil.parseObj(resultStr);
                            if (null != res.get("balance")) {
                                if ((Integer) res.get("balance") > 0) {
                                    log.info("{}: 恭喜您第" + (count + 1) + "次预约成功，明天不用排队啦！", metror.getName() + " " + metror.getPhone());
//                                    emailMessage = "恭喜您地铁进站预约成功，明天不用排队啦！地点为：" + metror.getLineName() + metror.getStationName() + res.get("stationEntrance") + "\n 请移步 北京地铁预约出行 公众号查看";
                                    String time = TimeRangeConverter.convertTimeRange(metror.getMetroTime());
                                    emailMessage = "恭喜您预约成功！请于下个工作日：" + time + " 前往"+ metror.getLineName() + metror.getStationName() + res.get("stationEntrance") + "扫码进站。"+ "\n 请移步 北京地铁预约出行 公众号查看";
                                    log.info(emailMessage);
                                    appointMentId = (String) res.get("appointmentId");
                                    flag = true;
                                }else{
                                    log.info("{}: 第" + (count + 1) + "次预约失败", metror.getName() + " " + metror.getPhone());
                                }
                            } else {
                                if(!StringUtils.isEmpty(res.get("title")) && "Unauthorized".equals(res.get("title"))){
                                    log.info("{}：您的授权已过期，无法进行预约！请尽快前往： https://www.huyoa.com/subway  登录授权！", metror.getName() + " " + metror.getPhone());
                                    emailMessage = "您的授权已过期，无法进行预约！请尽快前往： <a href=\"https://www.huyoa.com/subway\">https://www.huyoa.com/subway</a>  登录授权！";
                                    String emailHeader = "地铁预约服务授权到期提醒！！！";
                                    metror.setTokenFlag("N");
                                    /** 此处需添加微信通知 */
                                    sendWeChat.sendMessage(metror.getName() + " " + metror.getPhone(), null, metror.getPushPlusToken(), emailHeader, emailMessage);
                                    break;
                                }
                                log.info("{}: 第" + (count + 1) + "次预约失败", metror.getName() + " " + metror.getPhone());
                            }
                        } catch (Exception e) {
                            log.info("{}: 第" + (count + 1) + "次预约失败；" + "原因：异常【" + e.getMessage() + "】", metror.getName() + " " + metror.getPhone());
                        }
                    } else {
                        log.info("{}: 第" + (count + 1) + "次预约失败", metror.getName() + " " + metror.getPhone());
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    log.error(e.getMessage());
                }finally {
                    count++;
                }

            }

            /** 改为微信通知 */
            if (flag) {
                metror.setIsNeedOrder("true");
                String emailHeader = "地铁预约抢票成功通知";
                sendWeChat.sendMessage(metror.getName() + " " + metror.getPhone(), null, metror.getPushPlusToken(), emailHeader, emailMessage);
            } else {
                emailMessage = "您的地铁预约抢票 失败！！！(自动抢票时间为每日 12点、20点)，详情请联系管理员咨询";
                String emailHeader = "地铁预约抢票失败通知";
                metror.setIsNeedOrder("false");
                sendWeChat.sendMessage(metror.getName() + " " + metror.getPhone(), null, metror.getPushPlusToken(), emailHeader, emailMessage);
            }
            metror.setAppointMentId(appointMentId);
            metroService.updateMetror(metror);

//            log.info("地铁预约抢票定时任务执行完成");
        }

    }

    public void start(Metror metror) {
        try {
            Boolean aBoolean = false;
            /** 检查token是否过期 */
            if(!StringUtils.isEmpty(metror.getTokenFlag()) && "Y".equals(metror.getTokenFlag())){
                aBoolean = true;
            }else if (StringUtils.isEmpty(metror.getTokenFlag())){
                aBoolean = checkToken(metror);
            }
            if (aBoolean) {
                /** token没过期，直接预约 */
                startReservation(metror);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Boolean checkIsMetro(Metror metror) {
        //查询是否有预约记录，有则不预约
        String resultStrs = HttpRequest.get("https://webapi.mybti.cn/AppointmentRecord/GetAppointmentList?status=0&lastid=")
                .header(Header.CONTENT_TYPE, "application/json;charset=UTF-8")
                .header(Header.AUTHORIZATION, metror.getMetroToken())
                .header("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1")
                .timeout(10000)
                .execute().body();
        if (resultStrs != null && resultStrs.startsWith("[")) {
            JSONArray res = JSONUtil.parseArray(resultStrs);
            if (res.size() > 0) {
                log.info("{}：" + res.toString(),metror.getName() + " " + metror.getPhone());
                log.info("{}：已预约，不可重复预约", metror.getName() + " " + metror.getPhone());
                return true;
            }else {
                log.info("{}：待预约", metror.getName() + " " + metror.getPhone());
            }
        } else if (resultStrs != null && resultStrs.startsWith("{")) {
//            JSONObject jsonObject = JSONUtil.parseObj(resultStrs);
//            log.info(jsonObject.toString());
            log.info("{}：" + resultStrs.toString(),metror.getName() + " " + metror.getPhone());
            log.info("{}：token到期", metror.getName() + " " + metror.getPhone());
            return null;
        } else {
            log.info("{}：待预约", metror.getName() + " " + metror.getPhone());
        }
        return false;
    }

    public long getNeedTime(int hour, int minute, int second, int addDay, int... args) {
        Calendar calendar = Calendar.getInstance();
        if (addDay != 0) {
            calendar.add(Calendar.DATE, addDay);
        }
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        if (args.length == 1) {
            calendar.set(Calendar.MILLISECOND, args[0]);
        }
        return calendar.getTimeInMillis();
    }

    /**
     * 更新token
     */
    public Metror refreshToken(Metror metror) {
        try {
            String url = "https://webapi.mybti.cn/User/GetNewToken?refreshtoken=" + metror.getRefreshToken();
            String resultStrs = HttpRequest.get(url)
                    .header(Header.ACCEPT, "application/json, text/plain, */*")
                    .header("authorization", metror.getMetroToken())
                    .header("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Mobile Safari/537.36")
                    .timeout(10000)
                    .execute().body();
            if (!StringUtils.isEmpty(resultStrs)) {
                log.info(resultStrs);
                if (!"exception, please check http header errcode.".equals(resultStrs)) {
                    JSONObject res = JSONUtil.parseObj(resultStrs);
                    if (!ObjectUtils.isEmpty(res)) {
                        log.info(res.toString());
                        try {
                            metror.setMetroToken((String) res.get("accesstoken"));
                            metror.setRefreshToken((String) res.get("refreshtoken"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return metror;
    }

    public String LocalDateTimeToDateConversion(LocalDateTime localDateTime){
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String formattedDateTime = localDateTime.format(formatter);
        System.out.println(formattedDateTime);
        return formattedDateTime;
    }

    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 7, 28, 12, 30);
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String formattedDateTime = localDateTime.format(formatter);
        System.out.println(formattedDateTime);
    }
}
