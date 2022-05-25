package com.example.gladoscheckin.qd.controller;

import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.common.Status;
import com.example.gladoscheckin.qd.pojo.Power;
import com.example.gladoscheckin.qd.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PowerController {
    @Autowired
    private PowerService powerService;

    @PostMapping("insertPower")
    public AjaxResult insertPower(@RequestBody Power power){
        return powerService.insertPower(power);
    }

    @GetMapping("/selectPower")
    public AjaxResult selectPower(){
        List<Power> powers = powerService.selectPower();
        return AjaxResult.build(Status.SUCCESS,Status.SUCCESS_MSG,powers);
    }
}
