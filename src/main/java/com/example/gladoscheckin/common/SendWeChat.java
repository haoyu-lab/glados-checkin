package com.example.gladoscheckin.common;

import com.alibaba.fastjson.JSONObject;
import com.example.gladoscheckin.qd.pojo.SendWeChatVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.common
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2022/9/810:36
 */
@Slf4j
@Configuration
public class SendWeChat {

    public String sendMessage(String token, String title, String message)throws Exception{
        String sendUrl = "http://www.pushplus.plus/send";
        int socketTimeout = 120 * 1000;
        int connectTimeout = 120 * 1000;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String qdResponse = null;

        SendWeChatVO sendWeChatVO = new SendWeChatVO();
        sendWeChatVO.setTemplate("json");
        sendWeChatVO.setToken(token);
        sendWeChatVO.setTitle(title);
        sendWeChatVO.setContent(message);
        String json = JSONObject.toJSONString(sendWeChatVO);
        qdResponse = HttpUtil.sendPost(sendUrl, socketTimeout, connectTimeout, headers, null, json);
        JSONObject jsonObject = JSONObject.parseObject(qdResponse);
        int status = (int) jsonObject.get("code");
        if(status == 200){
            log.info("token用户："+token+",微信推送成功");
        }else{
            log.info("token用户："+token+",微信推送失败");
        }
        return null;
    }
}


