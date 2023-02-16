package com.example.gladoscheckin.metro.controller;

import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.common.Status;
import com.example.gladoscheckin.metro.service.MetroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetroController {

    @Autowired
    MetroService metroService;

    @GetMapping("/metroCheckin")
    public AjaxResult metroCheckin(){
        return metroService.metroCheckin();
    }
    @GetMapping("/metroCheckin1")
    public AjaxResult metroCheckin1(){
        return metroService.metroCheckin1();
    }

    @GetMapping("/searchMetro")
    public AjaxResult searchMetro(){
        return metroService.searchMetro();
    }

    @GetMapping("/refreshIsNeedOrder")
    public void refreshIsNeedOrder(){
        metroService.refreshIsNeedOrder();
    }

    @GetMapping("/initializeIsNeedOrder")
    public void initializeIsNeedOrder(){
        metroService.initializeIsNeedOrder();
    }
}
