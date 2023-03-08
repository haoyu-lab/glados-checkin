package com.example.gladoscheckin.metro.controller;

import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.common.Status;
import com.example.gladoscheckin.metro.RequestVO;
import com.example.gladoscheckin.metro.service.MetroService;
import com.example.gladoscheckin.pushsend.pojo.VICode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MetroController {

    @Autowired
    MetroService metroService;

    @GetMapping("/metroCheckin")
    public AjaxResult metroCheckin() throws Exception{
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

    @GetMapping("/getVlCode")
    public AjaxResult getVlCode(@RequestParam(name = "phone") String phone){
        return metroService.getVlCode(phone);
    }

    @PostMapping("/metorLogin")
    public AjaxResult metorLogin(@RequestBody VICode viCode){
        return metroService.metorLogin(viCode);
    }

    @PostMapping("/insertOrUpdateMetor")
    public AjaxResult insertOrUpdateMetor(@RequestBody RequestVO requestVO){
        return metroService.insertOrUpdateMetor(requestVO);
    }

    @GetMapping("/updateTokenFlag")
    public AjaxResult updateTokenFlag(){
        return metroService.updateTokenFlag();
    }
}
