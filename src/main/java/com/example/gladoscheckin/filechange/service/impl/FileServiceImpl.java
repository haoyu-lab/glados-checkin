package com.example.gladoscheckin.filechange.service.impl;

import com.example.gladoscheckin.common.FileUtil;
import com.example.gladoscheckin.filechange.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.filechange.service.impl
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2022/7/1314:28
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    public void downloadFile(String myfile, HttpServletResponse resp) throws UnsupportedEncodingException {
        String savePath = "D:\\iotest\\file\\2.jpeg";
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-Type", "image/jpeg");
        resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("2.jpeg", "UTF-8"));
        FileUtil.GenerateImageByInterface(myfile,savePath,resp);
    }

}
