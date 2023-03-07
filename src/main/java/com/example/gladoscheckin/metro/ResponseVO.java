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
    /** 信息 */
    private String message;
    /** 手机号 */
    private String phone;
    /** 预约token */
    private String metroToken;
    /** 推送加微信推送token */
    private String pushPlusToken;
    /** 预约时间段 */
    private String metroTime;
//    /** 刷新token */
//    private String refreshToken;
    /** 地铁线路 */
    private String lineName;
    /** 地铁站名称 */
    private String stationName;
    /**
     * 是否有效 Y是 N否
     */
    private String isVaild;
}
