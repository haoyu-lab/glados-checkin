package com.example.gladoscheckin.metro;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.metro
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2023/3/714:28
 */
@Data
public class RequestVO {
    /** 预约抢票token */
    private String metroToken;
    /** 姓名 */
    private String name;
    /** 推送加微信推送token */
    private String pushPlusToken;
    /** 手机号 */
    private String phone;
    /** 预约时间段 */
    private String metroTime;

    /** 刷新token */
    private String refreshToken;

    /** 地铁线路 */
    private String lineName;

    /** 地铁站名称 */
    private String stationName;
    /**
     * 是否有效 Y是 N否
     */
    private String isVaild;
}
