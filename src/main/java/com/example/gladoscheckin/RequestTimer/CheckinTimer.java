package com.example.gladoscheckin.RequestTimer;

import com.example.gladoscheckin.metro.service.CheckTmorrowService;
import com.example.gladoscheckin.metro.service.MetroService;
import com.example.gladoscheckin.qd.service.CheckinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Slf4j
public class CheckinTimer {

    @Autowired
    private CheckinService checkinService;
    @Autowired
    MetroService metroService;
    @Autowired
    CheckTmorrowService checkTmorrowService;

    @Scheduled(cron = "0 0 8 * * ?")
    public void checkin(){
        log.info("每天五点glados签到开始");
        checkinService.checkin();
        log.info("每天五点glados签到结束");
    }

    @Scheduled(cron = "0 0 12,20 * * ?")
    public void taskMetro(){
        log.info("每天12点01分,20点01分地铁预约抢票签到开始");
        metroService.metroCheckin();
        log.info("每天12点01分,20点01分地铁预约抢票签到结束");
    }

    @Scheduled(cron = "0 58 11,19 * * ?")
    public void refreshIsNeedOrder(){
        log.info("每天11点58,19点58查询并更新是否有预约记录 开始");
        metroService.refreshIsNeedOrder();
        log.info("每天11点58,19点58查询并更新是否有预约记录 结束");
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void initializeIsNeedOrder(){
        log.info("每天凌晨1点刷新是否预约记录字段 开始");
        metroService.initializeIsNeedOrder();
        log.info("每天凌晨1点刷新是否预约记录字段 结束");
    }

    @Scheduled(cron = "0 0 11 * * ?")
    public void checkTmorrow(){
        log.info("每天11点检查今天是否需要抢票 开始");
        checkTmorrowService.updateCheckTmorrow();
        log.info("每天11点检查今天是否需要抢票 结束");
    }

    @Scheduled(cron = "0 45 11,19 * * ?")
    public void checkTokenFlag(){
        log.info("每天十一点四十五修改用户token字段并推送消息， 开始");
        metroService.updateTokenFlag();
        log.info("每天十一点四十五修改用户token字段并推送消息 结束");
    }
}
