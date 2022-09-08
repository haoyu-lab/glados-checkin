package com.example.gladoscheckin.qd.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.common.HttpUtil;
import com.example.gladoscheckin.common.SendEmail;
import com.example.gladoscheckin.common.SendWeChat;
import com.example.gladoscheckin.qd.pojo.Power;
import com.example.gladoscheckin.qd.service.CheckinService;
import com.example.gladoscheckin.qd.service.PowerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CheckinServiceImpl implements CheckinService {
    @Autowired
    private SendEmail sendEmail;
    @Autowired
    private PowerService powerService;
    @Autowired
    private SendWeChat sendWeChat;
    @Override
    public AjaxResult checkin() {
        RestTemplate restTemplate = new RestTemplate();
        //从表中获取请求地址
        String sendKqUrl = "https://glados.one/api/user/checkin";
        log.info("glados签到接口地址{}", sendKqUrl);

        //从表中查询
        List<Power> powerList = powerService.selectPower();
        powerList.stream().forEach(e ->{
            log.info("待发送用户：{}",e.getEmail());
//            String email = e.getEmail();
            String cookie = e.getCookie();
            String json = "{\"token\":\"glados.network\"}";
            int socketTimeout = 120 * 1000;
            int connectTimeout = 120 * 1000;

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("cookie", cookie);
            String qdResponse = null;
            String statusResponse = null;
            String emailMessage = null;
            String emailHeader = null;
            String email = null;
            try {
                qdResponse = HttpUtil.sendPost(sendKqUrl, socketTimeout, connectTimeout, headers, null, json);
                JSONObject jsonObject = JSONObject.parseObject(qdResponse);
                int status = (int) jsonObject.get("code");
                String qdMessage = (String) jsonObject.get("message");
                if (status == 0) {
                    log.info("签到成功:{}", qdMessage);
                    emailHeader = "glados签到成功！";
                }
                if (status == 1) {
                    log.info("今日已签到:{}", qdMessage);
                    emailHeader = "glados今日已经签到！";
                }
                if (status == -2) {
                    log.info("签到失败:{}", qdMessage);
                    emailHeader = "glados签到失败！";
                }
                //调用glados"status"接口获取邮箱及剩余天数及剩余流量
                String sendStatusUrl = "https://glados.one/api/user/status";
                statusResponse = HttpUtil.sendGet(sendStatusUrl, cookie, null);
                JSONObject jsonObject1 = JSONObject.parseObject(statusResponse);
                if ((int) jsonObject1.get("code") == 0) {
                    //说明请求成功
                    JSONObject jsonObjectInner = (JSONObject) jsonObject1.get("data");
                    email = (String) jsonObjectInner.get("email");
                    String leftDays = (String) jsonObjectInner.get("leftDays");
                    leftDays = leftDays.substring(0,leftDays.indexOf("."));
                    Object traffic1 = jsonObjectInner.get("traffic");
                    if(traffic1 instanceof Integer){
                        if((Integer) traffic1 == 0){
                            BigDecimal formatTraffic = new BigDecimal(0);
                            emailMessage = "VIP剩余" + leftDays + "天，" + "本月已使用流量" + formatTraffic + "GB";
                        }else{
//                            Long traffic = (Long) jsonObjectInner.get("traffic");
                            BigDecimal formatTraffic = new BigDecimal((Integer)traffic1);
                            BigDecimal bigDecimal = new BigDecimal(1024);
                            formatTraffic = formatTraffic.divide(bigDecimal).divide(bigDecimal).divide(bigDecimal).setScale(2, BigDecimal.ROUND_HALF_UP);
                            emailMessage = "VIP剩余" + leftDays + "天，" + "本月已使用流量" + formatTraffic + "GB";
                        }
                    }else{
                        Long traffic = (Long) jsonObjectInner.get("traffic");
                        BigDecimal formatTraffic = new BigDecimal(traffic);

                        BigDecimal bigDecimal = new BigDecimal(1024);
                        formatTraffic = formatTraffic.divide(bigDecimal).divide(bigDecimal).divide(bigDecimal).setScale(2, BigDecimal.ROUND_HALF_UP);
                        emailMessage = "VIP剩余" + leftDays + "天，" + "本月已使用流量" + formatTraffic + "GB";
                    }

                    if(!ObjectUtils.isEmpty(emailMessage)){
                        log.info("用户{}：{}",e.getEmail(), emailMessage);
                    }
                }
                //调用邮箱接口发送
                //TODO 去除邮箱推送，改为微信推送
//                sendEmail.sendMessage(email,emailHeader,emailMessage);
                //调用微信推送接口发送
                sendWeChat.sendMessage(e.getPushPlusToken(),emailHeader,emailMessage);
            } catch (Exception e1) {
                e1.printStackTrace();
                try {
                    //TODO 去除邮箱推送，改为微信推送
//                    sendEmail.sendMessage(email,"glados签到服务异常",e1.getMessage());
                    sendWeChat.sendMessage(e.getPushPlusToken(),"glados签到服务异常",emailMessage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
//        String cookie = "_ga=GA1.2.831621257.1625583643; __stripe_mid=bbc14e93-1f8d-4dd8-999a-64cff70af1c48b7562; _gid=GA1.2.405101573.1653366910; koa:sess=eyJ1c2VySWQiOjc1ODI1LCJfZXhwaXJlIjoxNjc5Mjg3NDc1NDA0LCJfbWF4QWdlIjoyNTkyMDAwMDAwMH0=; koa:sess.sig=gdqXKtFNwu92-MB55S7HOyelVuI; _dd_s=logs=1&id=f853c020-b343-49e8-8246-fc4ce1b5c8ce&created=1653366909711&expire=1653369331864";
//
//        String json = "{\"token\":\"glados.network\"}";
//        int socketTimeout = 120 * 1000;
//        int connectTimeout = 120 * 1000;
//
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/json");
//        headers.put("cookie", cookie);
//        String qdResponse = null;
//        String statusResponse = null;
//        String emailMessage = null;
//        String emailHeader = null;
//        String email = null;
//        try {
//            qdResponse = HttpUtil.sendPost(sendKqUrl, socketTimeout, connectTimeout, headers, null, json);
//            JSONObject jsonObject = JSONObject.parseObject(qdResponse);
//            int status = (int) jsonObject.get("code");
//            String qdMessage = (String) jsonObject.get("message");
//            if (status == 0) {
//                log.info("签到成功:{}", qdMessage);
//                emailHeader = "glados签到成功！";
//            }
//            if (status == 1) {
//                log.info("今日已签到:{}", qdMessage);
//                emailHeader = "glados今日已经签到！";
//            }
//            if (status == -2) {
//                log.info("签到失败:{}", qdMessage);
//                emailHeader = "glados签到失败！";
//            }
//            //调用glados"status"接口获取邮箱及剩余天数及剩余流量
//            String sendStatusUrl = "https://glados.one/api/user/status";
//            statusResponse = HttpUtil.sendGet(sendStatusUrl, cookie, null);
//            JSONObject jsonObject1 = JSONObject.parseObject(statusResponse);
//            if ((int) jsonObject1.get("code") == 0) {
//                //说明请求成功
//                JSONObject jsonObjectInner = (JSONObject) jsonObject1.get("data");
//                email = (String) jsonObjectInner.get("email");
//                String leftDays = (String) jsonObjectInner.get("leftDays");
//                leftDays = leftDays.substring(0,leftDays.indexOf("."));
//                Long traffic = (Long) jsonObjectInner.get("traffic");
//                BigDecimal formatTraffic = new BigDecimal(traffic);
//
//                BigDecimal bigDecimal = new BigDecimal(1024);
//                formatTraffic = formatTraffic.divide(bigDecimal).divide(bigDecimal).divide(bigDecimal).setScale(2, BigDecimal.ROUND_HALF_UP);
//
//                emailMessage = "VIP剩余" + leftDays + "天，" + "本月已使用流量" + formatTraffic + "GB";
//
//            }
//            //调用邮箱接口发送
//            sendEmail.sendMessage(email,emailHeader,emailMessage);
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                sendEmail.sendMessage(email,"glados签到服务异常",e.getMessage());
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//        }
        System.out.println("===================签到结束===================");
        return AjaxResult.build2Success(true);
    }
}
