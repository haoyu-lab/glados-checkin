package com.example.gladoscheckin.RequestTimer;

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

    @Scheduled(cron = "0 0 5 * * ?")
    public void checkin(){
        log.info("每天五点glados签到开始");
        checkinService.checkin();
        log.info("每天五点glados签到结束");
    }

    @Scheduled(cron = "0 0 12,20 * * ?")
    public void taskMetro(){
        log.info("每天12点,20点地铁预约抢票签到开始");
        metroService.metroCheckin();
        log.info("每天12点,20点地铁预约抢票签到结束");
    }

    @Scheduled(cron = "0 58 11,19 * * ? ")
    public void refreshIsNeedOrder(){
        log.info("每天11点58,13点58查询并更新是否有预约记录 开始");
        metroService.refreshIsNeedOrder();
        log.info("每天11点58,13点58查询并更新是否有预约记录 结束");
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void initializeIsNeedOrder(){
        log.info("每天凌晨1点刷新是否预约记录字段 开始");
        metroService.initializeIsNeedOrder();
        log.info("每天凌晨1点刷新是否预约记录字段 结束");
    }
}
