package com.example.gladoscheckin.filezip.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author houhaoyu
 * @title: com.bfjz.wmannouncement.modules.filezip.service.impl
 * @projectName wealthmanagement
 * @description: TODO
 * @date 2023/3/2913:52
 */
public interface ImplCompressService {

    void imageConpress(MultipartFile file,String quality, HttpServletResponse response) throws IOException;
}
