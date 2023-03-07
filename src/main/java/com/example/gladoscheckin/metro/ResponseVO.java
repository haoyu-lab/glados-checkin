package com.example.gladoscheckin.metro;

import lombok.Builder;
import lombok.Data;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.metro
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2023/3/714:11
 */
@Data
@Builder
public class ResponseVO {
    //信息
    private String message;
    //手机号
    private String phone;
    //token
    private String metroToken;

}
