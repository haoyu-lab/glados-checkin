package com.example.gladoscheckin.click;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.click
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2023/2/2014:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("click")
public class Click {
    /** 点击次数 */
    @TableField("CLICKS_COUNT")
    private String clicksCount;
}
