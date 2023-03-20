package com.example.gladoscheckin.timingtask.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.common.Status;
import com.example.gladoscheckin.timingtask.common.QuartzConfig;
import com.example.gladoscheckin.timingtask.mapper.CronTaskMapper;
import com.example.gladoscheckin.timingtask.pojo.CheckTaskVO;
import com.example.gladoscheckin.timingtask.pojo.CronTask;
import com.example.gladoscheckin.timingtask.pojo.CronTaskVO;
import com.example.gladoscheckin.timingtask.service.CronTaskService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hhy
 * @since 2023-02-28
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CronTaskServiceImpl extends ServiceImpl<CronTaskMapper, CronTask> implements CronTaskService {

    public static Map<String, String> codeMap = new HashMap<String, String>();

    @Autowired
    private QuartzConfig quartzConfig;

    @Override
    public List<CronTask> findTask(String taskName){
//        if(ObjectUtils.isEmpty(cronTaskVO)){
//            throw new Exception("参数不可为空");
//        }
        QueryWrapper<CronTask> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(!StringUtils.isEmpty(taskName),CronTask::getTaskName,taskName);
        List<CronTask> cronTasks = baseMapper.selectList(queryWrapper);
        return cronTasks;
    }

    @Override
    public List<CronTask> findTasks() {
        QueryWrapper<CronTask> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(CronTask::getTaskStatus,"Y");
        List<CronTask> cronTasks = baseMapper.selectList(queryWrapper);
        return cronTasks;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult updateTask(CronTaskVO cronTaskVO) throws Exception {
        CronTask build = CronTask.builder().build();
        BeanUtils.copyProperties(cronTaskVO,build);

        //校验
        CheckTaskVO checkTaskVO = checkTask(build);
        if(!checkTaskVO.getIsTri()){
            return AjaxResult.build(Status.SERVER_ERROR, checkTaskVO.getTriMsg(), checkTaskVO.getTriMsg());
        }

        if(StringUtils.isEmpty(build.getTaskGroup())){
            build.setTaskGroup("reptile");
        }
        //查询数据库该数据
        CronTask cronTask = baseMapper.selectById(build.getId());
        if("Y".equals(cronTask.getTaskStatus())){
            //说明启动时已注册该任务
            Boolean aBoolean = quartzConfig.removeJob(cronTask.getTaskName());
            if(!aBoolean){
                return AjaxResult.build(Status.SERVER_ERROR, "定时任务删除出错", "定时任务删除出错");
            }

        }
        if("Y".equals(build.getTaskStatus())){
            //说明本次要新增任务
            //添加任务
            quartzConfig.insertJob(build);
        }

        baseMapper.updateById(build);

        return AjaxResult.build2Success(build);
    }

    private CheckTaskVO checkTask(CronTask cronTask){
        Boolean flag = CronExpression.isValidExpression(cronTask.getCronExpression());
        if(!flag){
            return CheckTaskVO.builder().isTri(false).triMsg("cron表达式不合法，请重新输入").build();
        }
        if(StringUtils.isEmpty(cronTask.getSearchKey())){
            return CheckTaskVO.builder().isTri(false).triMsg("标题关键字不可为空").build();
        }
        if(StringUtils.isEmpty(cronTask.getTaskName())){
            return CheckTaskVO.builder().isTri(false).triMsg("任务名称不可为空").build();
        }
        //校验名称
        QueryWrapper<CronTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CronTask::getTaskName,cronTask.getTaskName())
                .ne(Objects.nonNull(cronTask.getId()),CronTask::getId,cronTask.getId());
        List<CronTask> cronTasks = baseMapper.selectList(queryWrapper);
        if(cronTasks.size() > 0){
            return CheckTaskVO.builder().isTri(false).triMsg("已存在相同名称的任务，请重新输入定时任务名称").build();
        }
        return CheckTaskVO.builder().isTri(true).build();
    }

    @Override
    public AjaxResult insertTask(CronTaskVO cronTaskVO) throws Exception{
        CronTask cronTask = new CronTask();
        BeanUtils.copyProperties(cronTaskVO,cronTask);

        //校验
        CheckTaskVO checkTaskVO = checkTask(cronTask);
        if(!checkTaskVO.getIsTri()){
            return AjaxResult.build(Status.SERVER_ERROR, checkTaskVO.getTriMsg(), checkTaskVO.getTriMsg());
        }
        if(StringUtils.isEmpty(cronTask.getTaskGroup())){
            cronTask.setTaskGroup("reptile");
        }

        baseMapper.insert(cronTask);

        //添加任务
        quartzConfig.insertJob(cronTask);

        return AjaxResult.build2Success(true);
    }

    /** 废弃
    @PostConstruct
    private void init(){
        log.info("启动获取定时数据");
        List<CronTask> cronTasks = this.findTask(null);
        cronTasks.stream().forEach(e ->{
            codeMap.put(e.getTaskName(),e.getCronExpression());
        });
        try{
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(codeMap.get("reptileTask"));
            log.info("正在设置的爬虫定时时间：{}",codeMap.get("reptileTask"));
            // 按新的cronExpression表达式重新构建trigger
            trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());
            trigger = trigger.getTriggerBuilder().withIdentity(cronTrigger.getKey())
                    .withSchedule(scheduleBuilder).build();
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(cronTrigger.getKey(), trigger);
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
}
