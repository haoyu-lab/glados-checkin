package com.example.gladoscheckin.filechange.controller;

import com.example.gladoscheckin.filechange.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.filechange.controller
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2022/7/1314:28
 */
@Api(tags = {"文件转换"})
@RequestMapping("/file")
@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @ApiOperation(value = "base64转文件")
    @PostMapping("/base64changeFile")
    public void downloadFile(@RequestParam("myfile") String myfile, HttpServletResponse resp) throws Exception{
        fileService.downloadFile(myfile,resp);

    }
}
