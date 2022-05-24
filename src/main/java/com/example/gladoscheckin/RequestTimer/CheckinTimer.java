package com.example.gladoscheckin.RequestTimer;

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

    @Scheduled(cron = "0 0 0 * * ?")
    public void checkin(){
        log.info("每天凌晨glados签到开始");
        checkinService.checkin();
        log.info("每天凌晨glados签到结束");
    }
}
