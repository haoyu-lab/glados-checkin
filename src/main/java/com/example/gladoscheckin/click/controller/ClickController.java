package com.example.gladoscheckin.click.controller;

import com.example.gladoscheckin.click.service.ClickService;
import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.metro.service.MetroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.click.controller
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2023/2/2014:25
 */
@RestController
public class ClickController {
    @Autowired
    ClickService clickService;

    @GetMapping("/getClicks")
    public AjaxResult getClicks(){
        return clickService.getClicks();
    }
}
