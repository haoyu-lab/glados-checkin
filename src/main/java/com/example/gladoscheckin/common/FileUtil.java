package com.example.gladoscheckin.common;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.common
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2022/7/1314:36
 */
public class FileUtil {
    /**
     * 1.base64转图片
     * @param base64str1 base64码
     * @param savePath 图片路径
     * @return
     */
    public static boolean GenerateImage(String base64str1, String savePath) {
        //对字节数组字符串进行Base64解码并生成图片
        /**
         * 改编--需截取base64前缀方可转成功
         */
        String base64str = StringUtils.substringAfter(base64str1, "data:image/png;base64,");
        //
        if (base64str == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(base64str);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                //调整异常数据
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //文件夹不存在则自动创建
            File tempFile = new File(savePath);
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(tempFile);
            out.write(b);
            out.flush();
            out.close();
            System.out.println("*****转图片成功******");
            return true;
        } catch (Exception e) {
            System.out.println("*****转图片失败******");
            return false;
        }
    }

    /**
     * 2.base64转图片
     * @param base64Code base64码
     */
    public static void convertBase64ToImage(String base64Code,String savePath){
        BufferedImage image = null;
        byte[] imageByte = null;
        try {
            imageByte = DatatypeConverter.parseBase64Binary(base64Code);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(new ByteArrayInputStream(imageByte));
            bis.close();
//            File outputfile = new File("D:/sealImg.jpg");
            File outputfile = new File(savePath);
            ImageIO.write(image, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 图片转base64字符串
     *
     * @param imgFile 图片路径
     * @return
     */
    public static String imageToBase64Str(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }


    /**
     * 1.base64转图片
     * @param base64str1 base64码
     * @param savePath 图片路径
     * @return
     */
    public static boolean GenerateImageByInterface(String base64str1, String savePath, HttpServletResponse resp) {
        //对字节数组字符串进行Base64解码并生成图片
        /**
         * 改编--需截取base64前缀方可转成功
         */
        String base64str = StringUtils.substringAfter(base64str1, "data:image/png;base64,");
        //
        if (base64str == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(base64str);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                //调整异常数据
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }

            /**
            //TODO 生成图片到具体位置
            //文件夹不存在则自动创建
            File tempFile = new File(savePath);
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(tempFile);
             */
            //生成jpeg图片从接口导出
            ServletOutputStream out = null;
            out = resp.getOutputStream();
//            BufferedOutputStream bos = null;
//            bos = new BufferedOutputStream(out);

            out.write(b);
//            out.flush();
//            out.close();
            System.out.println("*****转图片成功******");
            return true;
        } catch (Exception e) {
            System.out.println("*****转图片失败******");
            return false;
        }
    }

}
