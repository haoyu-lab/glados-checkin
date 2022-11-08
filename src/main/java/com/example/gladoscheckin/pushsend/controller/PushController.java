package com.example.gladoscheckin.pushsend.controller;

import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.common.Status;
import com.example.gladoscheckin.pushsend.pojo.PushMessage;
import com.example.gladoscheckin.pushsend.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.pushsend.controller
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2022/9/814:42
 */
@RestController
public class PushController {

    @Autowired
    private PushService pushService;

    @PostMapping("/pushWeChat")
    public AjaxResult pushWeChat(@RequestBody PushMessage pushMessage){
        try{
            int count = pushService.pushWeChat(pushMessage);
            return AjaxResult.build(Status.SUCCESS,Status.SUCCESS_MSG,count);
        }catch (Exception e){
            return AjaxResult.build(Status.SERVER_ERROR, Status.SERVER_ERROR_MSG, e.getMessage());
        }
    }

    @PostMapping("/pushWeChatHtml")
    public AjaxResult pushWeChatHtml(@RequestBody PushMessage pushMessage){
        try{
            int count = pushService.pushWeChatHtml(pushMessage);
            return AjaxResult.build(Status.SUCCESS,Status.SUCCESS_MSG,count);
        }catch (Exception e){
            return AjaxResult.build(Status.SERVER_ERROR, Status.SERVER_ERROR_MSG, e.getMessage());
        }

    }
}
