package com.example.gladoscheckin.filezip.service.impl;

import com.example.gladoscheckin.filezip.common.VideoUtil;
import com.example.gladoscheckin.filezip.service.VideoCompressService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author houhaoyu
 * @title: com.bfjz.wmannouncement.modules.filezip.service.impl
 * @projectName wealthmanagement
 * @description: TODO
 * @date 2023/3/3014:43
 */
@Service
@Slf4j
public class VideoCompressServiceImpl implements VideoCompressService {

    InputStream imageinputStream = null;
    OutputStream imageoutputStream = null;

    BufferedOutputStream bos = null;
    ServletOutputStream out = null;
    FileInputStream input = null;

    @Override
    public void videoConpress(MultipartFile multipartFile,String rate) throws Exception{
        /** 文件multipartFile */
        String sourceName = VideoUtil.randomString(null,18) + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."),multipartFile.getOriginalFilename().length());
        File sourcefile = new File("D:\\iotest\\file\\" + sourceName);
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), sourcefile);

        File target = VideoUtil.compressionVideo(sourcefile,multipartFile.getOriginalFilename());

        //上传成功以后删除该临时文件
        if(sourcefile != null){
            sourcefile.delete();
            sourcefile.getParentFile().delete();
        }
    }
}
