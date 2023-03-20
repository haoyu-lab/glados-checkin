package com.example.gladoscheckin.timingtask.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.timingtask.pojo.CronTask;
import com.example.gladoscheckin.timingtask.pojo.CronTaskVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hhy
 * @since 2023-02-28
 */
public interface CronTaskService extends IService<CronTask> {

    List<CronTask> findTask(String taskName);

    List<CronTask> findTasks();

    AjaxResult updateTask(CronTaskVO cronTaskVO) throws Exception;

    AjaxResult insertTask(CronTaskVO cronTaskVO) throws Exception;
}
