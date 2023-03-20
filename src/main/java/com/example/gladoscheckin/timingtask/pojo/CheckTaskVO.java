package com.example.gladoscheckin.timingtask.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @author houhaoyu
 * @title: com.bfjz.wmannouncement.modules.timer.pojo
 * @projectName wealthmanagement
 * @description: TODO
 * @date 2023/3/1715:40
 */
@Data
@Builder
public class CheckTaskVO {

    /** 是否通过 */
    private Boolean isTri;

    /** 不通过原因 */
    private String triMsg;
}
