package com.example.gladoscheckin.metro;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetroVO {

    /**
     * 今天是否需要抢票
     */
    private Boolean isReservation = true;

    /**
     * 地铁签到token
     */
    private String authorization;

    /**
     * 待签到时间
     */
    private String time;

}
