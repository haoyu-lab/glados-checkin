package com.example.gladoscheckin.filezip.common;

import cn.hutool.core.util.StrUtil;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.info.AudioInfo;
import ws.schild.jave.info.VideoInfo;
/**
 * @author houhaoyu
 * @title: com.bfjz.wmannouncement.modules.filezip.common
 * @projectName wealthmanagement
 * @description: TODO
 * @date 2023/3/3014:55
 */

import java.io.File;
import java.math.BigDecimal;

import static cn.hutool.core.util.RandomUtil.randomInt;

/**
 * 视频压缩工具类
 */
public class VideoUtil {

    /**
     * 传视频File对象（这是一个具体的文件），返回压缩后File对象信息
     * @param source
     */
    public static File compressionVideo(File source, String picName) {
        if(source == null){
            return null;
        }
        String newPath = source.getAbsolutePath().substring(0, source.getAbsolutePath().lastIndexOf(File.separator)).concat(File.separator).concat(picName);
        File target = new File(newPath);
        try {
            MultimediaObject object = new MultimediaObject(source);
            // 根据视频大小来判断是否需要进行压缩
            int maxSize = 20;
            double mb = Math.ceil(source.length() / 1048576);
            int second = (int)object.getInfo().getDuration() / 1000;
            BigDecimal bd = new BigDecimal(String.format("%.4f", mb / second));
            System.out.println("开始压缩视频了--> 视频每秒平均 "+ bd +" MB ");
            // 视频 > 20MB, 或者每秒 > 0.5 MB 才做压缩
            boolean temp = mb > maxSize || bd.compareTo(new BigDecimal(0.5)) > 0;
            if(temp){
                long time = System.currentTimeMillis();
                // 视频压缩参数
                int maxAudioBitRate = 128000;
                int maxSamplingRate = 44100;
                int maxVideoBitRate = 800000;
                int maxFrameRate = 20;

                // 音频属性设置
                AudioInfo audioInfo = object.getInfo().getAudio();
                AudioAttributes audio = new AudioAttributes();
                // 设置音频比特率,单位:b (比特率越高，清晰度/音质越好，当然文件也就越大 128000 = 182kb)
                if(audioInfo.getBitRate() > maxAudioBitRate){
                    audio.setBitRate(new Integer(maxAudioBitRate));
                }
                // 设置重新编码的音频流中使用的声道数（1 =单声道，2 = 双声道（立体声））。
                // 如果未设置任何声道值，则编码器将选择默认值 0。
                audio.setChannels(audioInfo.getChannels());
                // 采样率越高声音的还原度越好，文件越大
                // 设置音频采样率，单位：赫兹 hz
                // 设置编码时候的音量值，未设置为0;如果256，则音量值不会改变
                audio.setVolume(256);
                if(audioInfo.getSamplingRate() > maxSamplingRate){
                    audio.setSamplingRate(maxSamplingRate);
                }

                // 视频编码属性配置
                VideoInfo videoInfo = object.getInfo().getVideo();
                VideoAttributes video = new VideoAttributes();
                //设置音频比特率,单位:b (比特率越高，清晰度/音质越好，当然文件也就越大 800000 = 800kb)
                if(videoInfo.getBitRate() > maxVideoBitRate){
                    video.setBitRate(maxVideoBitRate);
                }
                // 视频帧率：15 f / s  帧率越低，效果越差
                // 设置视频帧率（帧率越低，视频会出现断层，越高让人感觉越连续），视频帧率（Frame rate）是用于测量显示帧数的量度。
                // 所谓的测量单位为每秒显示帧数(Frames per Second，简：FPS）或“赫兹”（Hz）。
                if(videoInfo.getFrameRate() > maxFrameRate){
                    video.setFrameRate(maxFrameRate);
                }

                EncodingAttributes attr = new EncodingAttributes();
                attr.setOutputFormat("mp4");
                attr.setAudioAttributes(audio);
                attr.setVideoAttributes(video);
                // 设置线程数
//                attr.setEncodingThreads(Runtime.getRuntime().availableProcessors()/2);

                Encoder encoder = new Encoder();
                encoder.encode(new MultimediaObject(source), target, attr);

                System.out.println("压缩前大小： "+source.length()/1048576 + "MB" + " 压缩后大小：" + target.length()/1048576 + "MB");
                System.out.println("压缩总耗时：" + (System.currentTimeMillis() - time)/1000 + "s");
                return target;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(target.length() > 0){
                source.delete();
            }
        }
        return source;
    }

    /**
     * 视频压缩
     * @param source 源文件
     * @param rate 压缩比
     */
    public static File compre(File source ,String picName, Integer rate) {
        try {
            String newPath = source.getAbsolutePath().substring(0, source.getAbsolutePath().lastIndexOf(File.separator)).concat(File.separator).concat(picName);
            File target = new File(newPath);

            System.out.println("begin");
            long start = System.currentTimeMillis();

            // 音频编码属性配置
            AudioAttributes audio= new AudioAttributes();
            audio.setCodec("libmp3lame");
            // 设置音频比特率,单位:b (比特率越高，清晰度/音质越好，当然文件也就越大 56000 = 56kb)
            audio.setBitRate(new Integer(56000));
            // 设置重新编码的音频流中使用的声道数（1 =单声道，2 = 双声道（立体声））
            audio.setChannels(1);
            // 采样率越高声音的还原度越好，文件越大
            audio.setSamplingRate(new Integer(44100));

            // 视频编码属性配置
            VideoAttributes video=new VideoAttributes();
            // 设置编码
            video.setCodec("mpeg4");
            //设置音频比特率,单位:b (比特率越高，清晰度/音质越好，当然文件也就越大 5600000 = 5600kb)
            video.setBitRate(new Integer(5600000 / rate));
            // 设置视频帧率（帧率越低，视频会出现断层，越高让人感觉越连续）,这里 除1000是为了单位转换
            video.setFrameRate(new Integer(15));


            // 编码设置
            EncodingAttributes attr=new EncodingAttributes();
            attr.setOutputFormat("mp4");
            attr.setAudioAttributes(audio);
            attr.setVideoAttributes(video);

            // 设置值编码
            Encoder ec = new Encoder();
            ec.encode(new MultimediaObject(source), target, attr);


            System.out.println("end");
            long end = System.currentTimeMillis();

            System.out.println("压缩前大小： "+source.length()/1048576 + "MB" + " 压缩后大小：" + target.length()/1048576 + "MB");

            System.out.println("压缩耗时： " + (end -start)/1000 + "s");

            return target;
        } catch (EncoderException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return source;
    }

    public static String randomString(String baseString, int length) {
        if (StrUtil.isEmpty(baseString)) {
            baseString = "1234567890";
        }
        final StringBuilder sb = new StringBuilder(length);

        if (length < 1) {
            length = 1;
        }
        int baseLength = baseString.length();
        for (int i = 0; i < length; i++) {
            int number = randomInt(baseLength);
            sb.append(baseString.charAt(number));
        }
        return sb.toString();
    }
}