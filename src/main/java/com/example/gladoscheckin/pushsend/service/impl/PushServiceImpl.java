package com.example.gladoscheckin.pushsend.service.impl;

import com.example.gladoscheckin.common.SendWeChat;
import com.example.gladoscheckin.pushsend.service.PushService;
import com.example.gladoscheckin.qd.pojo.Power;
import com.example.gladoscheckin.qd.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.pushsend.service.impl
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2022/9/814:48
 */
@Service
public class PushServiceImpl implements PushService {
    @Autowired
    private PowerService powerService;
    @Autowired
    private SendWeChat sendWeChat;

    @Override
    public int pushWeChat(String message) {
        List<Power> powerList = powerService.selectPower();
        message = "尊敬的用户您好！\n"+"       "+message+"\n        感谢您的使用！";
        String finalMessage = message;
        AtomicInteger count = new AtomicInteger();
        powerList.forEach(e ->{
            try {
                sendWeChat.sendMessage(e.getPushPlusToken(),"GlaDOS签到服务更新通知", finalMessage);
                count.getAndIncrement();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        return count.get();
    }
}
