package com.example.gladoscheckin.filezip.service.impl;

import com.example.gladoscheckin.filezip.service.ImplCompressService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author houhaoyu
 * @title: com.bfjz.wmannouncement.modules.filezip.service.impl
 * @projectName wealthmanagement
 * @description: TODO
 * @date 2023/3/2913:53
 */
@Service
public class ImplCompressServiceImpl implements ImplCompressService {

    @Override
    public void imageConpress(MultipartFile multipartFile,String quality, HttpServletResponse response) throws IOException {
        /**对文件进行处理 */
        Image srcFile = ImageIO.read(multipartFile.getInputStream());
        /** 宽,高设定 */
        BufferedImage tag = new BufferedImage(srcFile.getWidth(null), srcFile.getHeight(null), BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage(srcFile, 0, 0, srcFile.getWidth(null), srcFile.getHeight(null), null);

        ServletOutputStream outputStream = response.getOutputStream();
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);
        JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
        /** 压缩质量 */
        float qualityy = 0.75f;
        if(!StringUtils.isEmpty(quality)){
            qualityy = Float.parseFloat(quality);
        }
        jep.setQuality(qualityy, true);
        encoder.encode(tag, jep);
    }
}
