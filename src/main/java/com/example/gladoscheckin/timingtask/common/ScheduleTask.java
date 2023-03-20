package com.example.gladoscheckin.timingtask.common;

import com.example.gladoscheckin.timingtask.pojo.CronTask;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author houhaoyu
 * @title: com.bfjz.wmannouncement.modules.timer.common
 * @projectName wealthmanagement
 * @description: TODO
 * @date 2023/2/2815:54
 */
@Configuration
@Component
@Slf4j
public class ScheduleTask  implements Job {

    //调用方法
//    ReptileService reptileService = MyBeanUtil.getBean(ReptileService.class);

//    CronTaskService cronTaskService = MyBeanUtil.getBean(CronTaskService.class);

    /** 废弃
    public void reptileTask(){
        RequestVO requestVO = new RequestVO();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(new Date());
        requestVO.setSeDate(time + "~" + time);

        List<CronTask> cronTasks = cronTaskService.findTask("reptileTask");
        CronTask cronTask = cronTasks.get(0);
        requestVO.setSearchkey(cronTask.getSearchKey());
        if("Y".equals(cronTask.getTaskStatus())){
            reptileService.reptileFile(requestVO);
        }
    }*/

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //方法体
        log.info("定时方法执行");
    }
}
