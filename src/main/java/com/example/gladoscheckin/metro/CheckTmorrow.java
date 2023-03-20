package com.example.gladoscheckin.metro;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.metro
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2023/2/2710:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("CHECK_TOMORROW")
public class CheckTmorrow {

    /** 明日是否需要抢票 */
    @TableField("TOMORROW_IS_FLAG")
    private String tomorrowIsFlag;

    @TableField("TODAY_IS_FLAG")
    private String todayIsFlag;
}
