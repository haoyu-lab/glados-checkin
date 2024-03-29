package com.example.gladoscheckin.pushsend.service.impl;

import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.common.SendWeChat;
import com.example.gladoscheckin.metro.Metror;
import com.example.gladoscheckin.metro.service.MetroService;
import com.example.gladoscheckin.pushsend.pojo.PushMessage;
import com.example.gladoscheckin.pushsend.service.PushService;
import com.example.gladoscheckin.qd.pojo.Power;
import com.example.gladoscheckin.qd.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    @Autowired
    private MetroService metroService;

    @Override
    public int pushWeChat(PushMessage pushMessage) throws Exception{

        String message = "尊敬的用户您好！\n"+"&nbsp;&nbsp;&nbsp;&nbsp;"+pushMessage.getMessage()+"\n 感谢您的使用！";
        String finalMessage = message;
        AtomicInteger count = new AtomicInteger();
        if("glados".equals(pushMessage.getService())){
            List<Power> powerList = powerService.selectPower();
            if(!StringUtils.isEmpty(pushMessage.getUser())){
                powerList = powerList.stream().filter(e -> e.getEmail().contains(pushMessage.getUser())).collect(Collectors.toList());
            }
            powerList.forEach(e ->{
                try {
                    sendWeChat.sendMessage(null, e.getEmail(), e.getPushPlusToken(),pushMessage.getTitle(), finalMessage);
                    count.getAndIncrement();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
        }else if("metro".equals(pushMessage.getService())){
            //地铁预约服务通知
            AjaxResult ajaxResult = metroService.searchMetro();
            if(ajaxResult.getStatus() == 200){
                List<Metror> metrors = (List<Metror>)ajaxResult.getBody();
                if(!StringUtils.isEmpty(pushMessage.getUser())){
                    metrors = metrors.stream().filter(e -> e.getPhone().contains(pushMessage.getUser())).collect(Collectors.toList());
                }
                metrors.forEach(e ->{
                    try {
                        sendWeChat.sendMessageHtml(e.getName(), null, e.getPushPlusToken(),pushMessage.getTitle(), finalMessage);
                        count.getAndIncrement();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });
            }else{
                throw new Exception();
            }
        }

        return count.get();
    }

    @Override
    public int pushWeChatHtml(PushMessage pushMessage) throws Exception{
        AtomicInteger count = new AtomicInteger();
        if("glados".equals(pushMessage.getService())){
            //签到服务通知
            List<Power> powerList = powerService.selectPower();
//            powerList = powerList.stream().filter(e -> e.getEmail().contains("1103455")).collect(Collectors.toList());
            powerList.forEach(e ->{
                try {
                    sendWeChat.sendMessage(null, e.getEmail(), e.getPushPlusToken(),"GlaDOS签到服务通知", pushMessage.getMessage());
                    count.getAndIncrement();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
        }else if("metro".equals(pushMessage.getService())){
            //地铁预约服务通知
            AjaxResult ajaxResult = metroService.searchMetro();
            if(ajaxResult.getStatus() == 200){
                List<Metror> metrors = (List<Metror>)ajaxResult.getBody();
//                metrors = metrors.stream().filter(e -> e.getPhone().contains("15135842296")).collect(Collectors.toList());
                metrors.forEach(e ->{
                    try {
                        sendWeChat.sendMessageHtml(e.getName(), null, e.getPushPlusToken(),"地铁预约服务通知", pushMessage.getMessage());
                        count.getAndIncrement();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });
            }else{
                throw new Exception();
            }
        }
        return count.get();
    }
}
