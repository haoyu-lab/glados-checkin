package com.example.gladoscheckin.pushsend.controller;

import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.common.Status;
import com.example.gladoscheckin.pushsend.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    public AjaxResult pushWeChat(String message){
        int count = pushService.pushWeChat(message);
        return AjaxResult.build(Status.SUCCESS,Status.SUCCESS_MSG,count);
    }
}
