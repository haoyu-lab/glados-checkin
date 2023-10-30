package com.example.gladoscheckin.filezip.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author houhaoyu
 * @title: com.bfjz.wmannouncement.modules.filezip.service
 * @projectName wealthmanagement
 * @description: TODO
 * @date 2023/3/3014:43
 */
public interface VideoCompressService {

    void videoConpress(MultipartFile file,String rate) throws Exception;
}
