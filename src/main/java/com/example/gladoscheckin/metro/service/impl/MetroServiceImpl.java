package com.example.gladoscheckin.metro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.common.Status;
import com.example.gladoscheckin.metro.MetroVO;
import com.example.gladoscheckin.metro.Metror;
import com.example.gladoscheckin.metro.mapper.MetrorMapper;
import com.example.gladoscheckin.metro.service.MetroService;
import com.example.gladoscheckin.metro.service.TaskUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

@Service
@Slf4j
public class MetroServiceImpl extends ServiceImpl<MetrorMapper, Metror> implements MetroService {

    @Autowired
    TaskUtils taskUtils;
    @Autowired
    @Qualifier("asyncTaskExecutor")
    private ThreadPoolTaskExecutor asyncTaskExecutor;


    @Override
    public AjaxResult metroCheckin() {
        //查询数据
        QueryWrapper<Metror> queryWrapper = new QueryWrapper<>();
        List<Metror> metrors = baseMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(metrors)){
            return AjaxResult.build(Status.SERVER_ERROR,"无预约用户","无预约用户");
        }

        /** 检查今天是否需要抢票 */
        Boolean isReservation = true;
        taskUtils.checkTomorrowIsHoliday(isReservation);
        if(isReservation){
//            List<FutureTask<List<Void>>> fTaskes = new ArrayList<>(index);
            metrors.forEach(e ->{
                //发送通知
                CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
                    taskUtils.start(e);
                },asyncTaskExecutor);
            });

        }
        return AjaxResult.build2Success(true);
    }

    @Override
    public AjaxResult searchMetro() {
        QueryWrapper<Metror> queryWrapper = new QueryWrapper<>();
        List<Metror> metrors = baseMapper.selectList(queryWrapper);
        return AjaxResult.build2Success(metrors);
    }

    @Override
    public void refreshIsNeedOrder(){
        QueryWrapper<Metror> queryWrapper = new QueryWrapper<>();
        List<Metror> metrors = baseMapper.selectList(queryWrapper);
        metrors.forEach(e ->{
            Boolean aBoolean = taskUtils.checkIsMetro(e);
            if(aBoolean){
                e.setIsNeedOrder("true");
                baseMapper.updateById(e);
            }else{
                e.setIsNeedOrder("false");
                baseMapper.updateById(e);
            }
        });
    }

    @Override
    public void initializeIsNeedOrder(){
        QueryWrapper<Metror> queryWrapper = new QueryWrapper<>();
        List<Metror> metrors = baseMapper.selectList(queryWrapper);
        metrors.forEach(e -> {
            e.setIsNeedOrder("false");
        });
        this.saveOrUpdateBatch(metrors);
    }
}
