package com.example.gladoscheckin.metro.controller;

import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.common.Status;
import com.example.gladoscheckin.metro.RequestVO;
import com.example.gladoscheckin.metro.service.MetroService;
import com.example.gladoscheckin.pushsend.pojo.VICode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"地铁预约"})
@RestController
public class MetroController {

    @Autowired
    MetroService metroService;

    @ApiOperation(value = "地铁预约")
    @GetMapping("/metroCheckin")
    public AjaxResult metroCheckin() throws Exception{
        return metroService.metroCheckin();
    }
    @ApiOperation(value = "测试方法")
    @GetMapping("/metroCheckin1")
    public AjaxResult metroCheckin1(){
        return metroService.metroCheckin1();
    }

    @ApiOperation(value = "查询预约用户")
    @GetMapping("/searchMetro")
    public AjaxResult searchMetro(){
        return metroService.searchMetro();
    }

    @ApiOperation(value = "查询并更新是否有预约记录")
    @GetMapping("/refreshIsNeedOrder")
    public void refreshIsNeedOrder(){
        metroService.refreshIsNeedOrder();
    }

    @ApiOperation(value = "刷新是否预约记录字段")
    @GetMapping("/initializeIsNeedOrder")
    public void initializeIsNeedOrder(){
        metroService.initializeIsNeedOrder();
    }

    @ApiOperation(value = "获取验证码")
    @GetMapping("/getVlCode")
    public AjaxResult getVlCode(@RequestParam(name = "phone") String phone){
        return metroService.getVlCode(phone);
    }

    @ApiOperation(value = "授权登录")
    @PostMapping("/metorLogin")
    public AjaxResult metorLogin(@RequestBody VICode viCode) throws Exception{
        return metroService.metorLogin(viCode);
    }

    @ApiOperation(value = "新增或修改预约用户信息")
    @PostMapping("/insertOrUpdateMetor")
    public AjaxResult insertOrUpdateMetor(@RequestBody RequestVO requestVO){
        return metroService.insertOrUpdateMetor(requestVO);
    }

    @ApiOperation(value = "修改授权字段")
    @GetMapping("/updateTokenFlag")
    public AjaxResult updateTokenFlag(){
        return metroService.updateTokenFlag();
    }

    @ApiOperation("进站后自动预约")
    @GetMapping("/getSubwayOrder")
    public AjaxResult getSubwayOrder(){
        return metroService.getSubwayOrder();
    }

    @ApiOperation("修改下次预约ID")
    @GetMapping("updateAppointMentId")
    public AjaxResult updateAppointMentId(){
        return metroService.updateAppointMentId();
    }
}
