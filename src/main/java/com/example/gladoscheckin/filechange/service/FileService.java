package com.example.gladoscheckin.filechange.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.filechange.service
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2022/7/1314:27
 */
public interface FileService {

    void downloadFile(String myfile, HttpServletResponse resp) throws UnsupportedEncodingException;
}
