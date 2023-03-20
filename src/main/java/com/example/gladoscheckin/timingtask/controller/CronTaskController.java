package com.example.gladoscheckin.timingtask.controller;


import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.timingtask.pojo.CronTask;
import com.example.gladoscheckin.timingtask.pojo.CronTaskVO;
import com.example.gladoscheckin.timingtask.service.CronTaskService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hhy
 * @since 2023-02-28
 */
@Api(tags = {"动态定时任务"})
@RestController
@RequestMapping("/timer/cronTask")
public class CronTaskController {

    @Autowired
    private CronTaskService cronTaskService;

    @PostMapping("/findTask")
    public AjaxResult findTask(@RequestBody CronTaskVO cronTaskVO){
        try{
            List<CronTask> cronTasks = cronTaskService.findTask(cronTaskVO.getTaskName());
            return AjaxResult.build2Success(cronTasks);
        }catch (Exception e){
            return AjaxResult.build2ServerError(false);
        }
    }

    @PostMapping("/updateTask")
    public AjaxResult updateTask(@RequestBody CronTaskVO cronTaskVO) throws Exception {
        return cronTaskService.updateTask(cronTaskVO);
    }

    @PostMapping("/insertTask")
    public AjaxResult insertTask(@RequestBody CronTaskVO cronTaskVO) throws Exception {
        return cronTaskService.insertTask(cronTaskVO);
    }
}

