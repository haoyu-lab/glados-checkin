package com.example.gladoscheckin.qd.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.common.HttpUtil;
import com.example.gladoscheckin.common.SendEmail;
import com.example.gladoscheckin.qd.service.CheckinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CheckinServiceImpl implements CheckinService {
    @Autowired
    private SendEmail sendEmail;
    @Override
    public AjaxResult checkin() {
        RestTemplate restTemplate = new RestTemplate();
        //从表中获取请求地址
        String sendKqUrl = "https://glados.one/api/user/checkin";
        log.info("glados签到接口地址{}", sendKqUrl);

        String cookie = "_ga=GA1.2.831621257.1625583643; __stripe_mid=bbc14e93-1f8d-4dd8-999a-64cff70af1c48b7562; _gid=GA1.2.405101573.1653366910; koa:sess=eyJ1c2VySWQiOjc1ODI1LCJfZXhwaXJlIjoxNjc5Mjg3NDc1NDA0LCJfbWF4QWdlIjoyNTkyMDAwMDAwMH0=; koa:sess.sig=gdqXKtFNwu92-MB55S7HOyelVuI; _dd_s=logs=1&id=f853c020-b343-49e8-8246-fc4ce1b5c8ce&created=1653366909711&expire=1653369331864";

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
                emailHeader = "glados重复签到";
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
                Long traffic = (Long) jsonObjectInner.get("traffic");
                BigDecimal formatTraffic = new BigDecimal(traffic);

                BigDecimal bigDecimal = new BigDecimal(1024);
                formatTraffic = formatTraffic.divide(bigDecimal).divide(bigDecimal).divide(bigDecimal).setScale(2, BigDecimal.ROUND_HALF_UP);

                emailMessage = "VIP剩余" + leftDays + "天，" + "本月已使用流量" + formatTraffic + "GB";

            }
            //调用邮箱接口发送
            sendEmail.sendMessage(email,emailHeader,emailMessage);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                sendEmail.sendMessage(email,"glados签到服务异常",e.getMessage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        System.out.println("==================================================");
        return null;
    }
}
