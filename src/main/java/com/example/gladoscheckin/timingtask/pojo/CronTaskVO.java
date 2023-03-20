package com.example.gladoscheckin.timingtask.pojo;

import lombok.Data;

/**
 * @author houhaoyu
 * @title: com.bfjz.wmannouncement.modules.timer.pojo
 * @projectName wealthmanagement
 * @description: TODO
 * @date 2023/2/2813:52
 */
@Data
public class CronTaskVO {
    private Long id;
    private String taskName;
    private String taskStatus;
    private String cronExpression;
    private String searchKey;
}
