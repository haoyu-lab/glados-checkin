package com.example.gladoscheckin.aliyuncheckin.pojo;/**
 * @title: com.example.gladoscheckin.aliyuncheckin.pojo
 * @author houhaoyu
 * @date 2023/10/27 15:06
 */

import lombok.Data;

/**
 * @title: com.example.gladoscheckin.aliyuncheckin.pojo
 * @author houhaoyu
 * @date 2023/10/27 15:06
 */
@Data
public class AliyunCheckinVO {

    private String name;

    private String accessToken;

    private int signInCount;

    private Boolean result = false;

    private String awardNotice;

    private String tasknotice;
}
