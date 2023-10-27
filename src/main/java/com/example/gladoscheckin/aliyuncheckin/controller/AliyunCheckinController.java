package com.example.gladoscheckin.aliyuncheckin.controller;

import com.example.gladoscheckin.common.AjaxResult;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;

import lombok.var;

import java.util.List;

import com.example.gladoscheckin.aliyuncheckin.service.AliyunCheckinService;
import com.example.gladoscheckin.aliyuncheckin.pojo.AliyunCheckin;

import java.text.SimpleDateFormat;
import javax.annotation.Resource;

/**
 * @author hhy
 * @since 2023-10-27
 */
@RestController
@RequestMapping("aliyun/")
@Api(value = "AliyunCheckinController", tags = "")
public class AliyunCheckinController {

    @Resource
    public AliyunCheckinService iAliyunCheckinService;

    /**
     * @Author houhaoyu
     * @Description 阿里云自动签到
     * @date 2023/10/27 14:52
     **/
    @PostMapping("aliyunCheckin")
    public AjaxResult aliyunCheckin(){
        return iAliyunCheckinService.aliyunCheckin();
    }
}
