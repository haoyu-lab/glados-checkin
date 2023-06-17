package com.example.gladoscheckin.metro;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.metro
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2022/10/2413:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("metror")
public class Metror {
    /** 预约抢票token */
    @TableField("METRO_TOKEN")
    private String metroToken;
    /** 姓名 */
    @TableField("NAME")
    private String name;
    /** 推送加微信推送token */
    @TableField("PUSH_PLUS_TOKEN")
    private String pushPlusToken;
    /** 手机号 */
    @TableId("PHONE")
    private String phone;
    /** 预约抢票时间 */
    @TableField("METRO_TIME")
    private String metroTime;

    /** 刷新token */
    @TableField("REFRESH_TOKEN")
    private String refreshToken;

    /** 地铁线路 */
    @TableField("LINE_NAME")
    private String lineName;

    /** 地铁站名称 */
    @TableField("STATION_NAME")
    private String stationName;

    /** 第二天是否已预约 true false */
    @TableField("IS_NEED_ORDER")
    private String isNeedOrder;

    /**
     * 是否有效 Y是 N否
     */
    @TableField("IS_VALID")
    private String isVaild;

    /**
     * token是否有效 Y是 N否
     */
    @TableField("TOKEN_FLAG")
    private String tokenFlag;

    /**
     * 下次预约ID
     */
    @TableField("APPOINT_MENT_ID")
    private String appointMentId;

    @TableField(exist = false)
    private String weekDate;

//    /**
//     * 今天是否需要抢票
//     */
//    @TableField(exist = false)
//    private Boolean isReservation = true;
}
