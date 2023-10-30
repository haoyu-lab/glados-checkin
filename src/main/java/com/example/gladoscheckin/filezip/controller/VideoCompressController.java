package com.example.gladoscheckin.filezip.controller;

import com.example.gladoscheckin.filezip.service.VideoCompressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author houhaoyu
 * @title: com.bfjz.wmannouncement.modules.filezip.controller
 * @projectName wealthmanagement
 * @description: TODO
 * @date 2023/3/3014:42
 */
@Api(tags = {""})
@RestController
@Slf4j
public class VideoCompressController {
    @Autowired
    private VideoCompressService videoCompressService;

    @ApiOperation("压缩视频")
    @PostMapping("/videoConpress")
    public void videoConpress(MultipartFile file,@RequestParam(name = "rate") String rate) throws Exception{
        videoCompressService.videoConpress(file,rate);
    }
}
