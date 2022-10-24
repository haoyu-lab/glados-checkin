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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

@Service
public class MetroServiceImpl extends ServiceImpl<MetrorMapper, Metror> implements MetroService {

    @Autowired
    TaskUtils taskUtils;

    @Override
    public AjaxResult metroCheckin() {
        //查询数据
        QueryWrapper<Metror> queryWrapper = new QueryWrapper<>();
        List<Metror> metrors = baseMapper.selectList(queryWrapper);
//        String authorization = "YTliYmQzNDQtMzE5Zi00NjcxLTgyYTMtZmQwNzY5YzA5ZTk2LDE2NjcyMDk2NzE1MDMsQ2QvcnlMYVZaZS8yQ0VQY2NlaFZ1NGM0U2MwPQ==";
//        String time = "0800-0810";
//        MetroVO metroVO = MetroVO.builder().authorization(authorization)
//                .time(time).build();
        if(CollectionUtils.isEmpty(metrors)){
            return AjaxResult.build(Status.SERVER_ERROR,"无预约用户","无预约用户");
        }

        /** 检查今天是否需要抢票 */
        Boolean isReservation = true;
        taskUtils.checkTomorrowIsHoliday(isReservation);
        if(isReservation){
            metrors.forEach(e ->{
                //发送通知
                CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
                    taskUtils.start(e);
                });
            });
        }
        return AjaxResult.build2Success(true);
    }
}
