package com.example.gladoscheckin.qd.controller;

import com.example.gladoscheckin.RequestTimer.CheckinTimer;
import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.qd.service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckinController {

    @Autowired
    private CheckinService checkinService;

    @Autowired
    private CheckinTimer checkinTimer;

    @GetMapping("/checkin")
    public AjaxResult checkin(){
        return checkinService.checkin();
    }

    @PostMapping("/checkinTimer")
    public void checkinTimer(){
        checkinTimer.checkin();
    }
}
