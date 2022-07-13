package com.example.gladoscheckin.filechange.controller;

import com.example.gladoscheckin.filechange.service.FileService;
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
@RequestMapping("/file")
@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/base64changeFile")
    public void downloadFile(@RequestParam("myfile") String myfile, HttpServletResponse resp) throws Exception{
        fileService.downloadFile(myfile,resp);

    }
}
