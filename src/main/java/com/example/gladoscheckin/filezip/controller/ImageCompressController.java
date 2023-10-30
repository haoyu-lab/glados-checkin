package com.example.gladoscheckin.filezip.controller;

import com.example.gladoscheckin.filezip.service.ImplCompressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author houhaoyu
 * @title: com.bfjz.wmannouncement.modules.filezip.controller
 * @projectName wealthmanagement
 * @description: TODO
 * @date 2023/3/2913:51
 */
@Api(tags = {""})
@RestController
@Slf4j
public class ImageCompressController {

    @Autowired
    private ImplCompressService implCompressService;

    @ApiOperation("压缩图片")
    @PostMapping("/imageConpress")
    public void imageConpress(MultipartFile file, @RequestParam(name = "quality") String quality, HttpServletResponse response) throws Exception{
        implCompressService.imageConpress(file,quality,response);
    }
}
