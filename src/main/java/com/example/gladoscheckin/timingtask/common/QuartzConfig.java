package com.example.gladoscheckin.timingtask.common;

import com.example.gladoscheckin.timingtask.pojo.CronTask;
import com.example.gladoscheckin.timingtask.service.CronTaskService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author houhaoyu
 * @title: com.bfjz.wmannouncement.modules.timer.common
 * @projectName wealthmanagement
 * @description: TODO
 * @date 2023/3/1614:19
 */
@Configuration
@Slf4j
public class QuartzConfig {
    @Autowired
    private CronTaskService cronTaskService;

    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    //获取任务调度器
    static Scheduler scheduler;

    static {
        try {
            scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public static Map<String,TriggerKey> jobMap = new ConcurrentHashMap<String, TriggerKey>();
    public static Map<Trigger, CronTask> taskMap = new ConcurrentHashMap<Trigger,CronTask>();

    public SchedulerFactory getSchedulerFactory(){
        return schedulerFactory;
    }

    @PostConstruct
    public void initQuartzFactory() throws Exception{
        //查询任务
        List<CronTask> tasks = cronTaskService.findTasks();
        if(tasks.size() > 0){

            tasks.stream().forEach(e ->{

                //获取任务详细信息
                JobDetail jobDetail = JobBuilder.newJob(ScheduleTask.class)
                        //任务名称和组构成任务key
                        .withIdentity(e.getTaskName(), e.getTaskGroup())
//                        .withDescription("reptileTask")
                        .build();


                //创建触发器key
                TriggerKey triggerKey = new TriggerKey(e.getTaskName(),e.getTaskGroup());

                jobMap.put(e.getTaskName(),triggerKey);

                // 触发器
                Trigger tigger = TriggerBuilder.newTrigger()
                        //触发器key唯一标识
                        .withIdentity(e.getTaskName(), e.getTaskGroup())
                        //调度开始时间
//                        .startAt(DateBuilder.futureDate(1, IntervalUnit.SECOND))
                        //调度规则
                        .withSchedule(CronScheduleBuilder.cronSchedule(e.getCronExpression()))
                        .build();
                taskMap.put(tigger,e);

                try {
                    log.info("正在设置的定时任务：{}",e.getTaskName());
                    log.info("cron表达式为：{}",e.getCronExpression());
                    scheduler.scheduleJob(jobDetail, tigger);
                    // 启动
                    if (!scheduler.isShutdown()) {
                        scheduler.start();
                    }
                } catch (SchedulerException schedulerException) {
                    schedulerException.printStackTrace();
                }
            });

//            jobMap.keySet().forEach(e ->{
//                Trigger trigger = null;
//                try {
//                    trigger = scheduler.getTrigger(jobMap.get(e));
//                    log.info(trigger.getKey().getName());
//                    log.info(trigger.getKey().getGroup());
//                    log.info(trigger.getKey().getGroup());
//
//                } catch (SchedulerException schedulerException) {
//                    schedulerException.printStackTrace();
//                }
//            });

        }
    }


    public Boolean insertJob(CronTask cronTask) throws Exception{
        try{
            //添加任务
            JobDetail jobDetail;
            jobDetail = JobBuilder.newJob(ScheduleTask.class)
                    //任务名称和组构成任务key
                    .withIdentity(cronTask.getTaskName(), cronTask.getTaskGroup())
//                        .withDescription("reptileTask")
                    .build();

            //创建触发器key
            TriggerKey triggerKey;
            triggerKey = new TriggerKey(cronTask.getTaskName(),cronTask.getTaskGroup());

            QuartzConfig.jobMap.put(cronTask.getTaskName(),triggerKey);
            // 触发器
            Trigger tigger;
            tigger = TriggerBuilder.newTrigger()
                    //触发器key唯一标识
                    .withIdentity(cronTask.getTaskName(), cronTask.getTaskGroup())
                    //调度开始时间
//                        .startAt(DateBuilder.futureDate(1, IntervalUnit.SECOND))
                    //调度规则
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronTask.getCronExpression()))
                    .build();

            QuartzConfig.taskMap.put(tigger,cronTask);
            log.info("正在新增的定时任务：{}",cronTask.getTaskName());
            log.info("cron表达式为：{}",cronTask.getCronExpression());
            //添加到调度器中
            scheduler.scheduleJob(jobDetail, tigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Boolean removeJob(String taskName) throws Exception{
        TriggerKey triggerKey = jobMap.get(taskName);
        //通过触发器key从调度器中获取触发器
        Trigger trigger = scheduler.getTrigger(triggerKey);
        //删除任务
        log.info("正在删除的定时任务：{}",taskName);
        boolean flag = scheduler.deleteJob(trigger.getJobKey());
        if(flag){
            //删除本地缓存
            taskMap.remove(trigger);
            jobMap.remove(taskName);
            return true;
        }
        return false;
    }
}
