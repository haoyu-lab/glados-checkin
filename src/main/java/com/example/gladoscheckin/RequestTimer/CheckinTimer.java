package com.example.gladoscheckin.RequestTimer;

import com.example.gladoscheckin.aliyuncheckin.service.AliyunCheckinService;
import com.example.gladoscheckin.csdnrefresh.service.CsdnService;
import com.example.gladoscheckin.metro.service.CheckTmorrowService;
import com.example.gladoscheckin.metro.service.MetroService;
import com.example.gladoscheckin.qd.service.CheckinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@Slf4j
public class CheckinTimer {

    @Autowired
    private CheckinService checkinService;
    @Autowired
    MetroService metroService;
    @Autowired
    CheckTmorrowService checkTmorrowService;

    @Autowired
    private CsdnService csdnService;

    @Autowired
    private AliyunCheckinService aliyunCheckinService;

//    @Scheduled(cron = "0 0 8,13,19 * * ?")
    public void checkin(){
        log.info("每天8,13,19点glados签到开始");
        checkinService.checkin();
        log.info("每天8,13,19点glados签到结束");
    }

    @Scheduled(cron = "0 0 5 * * ?")
    public void cleanPower(){
        log.info("每天5点清理glados签到表开始");
        checkinService.cleanPower();
        log.info("每天5点清理glados签到表结束");
    }

    @Scheduled(cron = "0 0 12,20 * * ?")
    public void taskMetro(){
        log.info("每天12点0分,20点0分地铁预约抢票签到开始");
        try {
            metroService.metroCheckin();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("每天12点0分,20点0分地铁预约抢票签到结束");
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

    @Scheduled(cron = "0 0 6 * * ?")
    public void checkTmorrow(){
        log.info("每天6点检查今天是否需要抢票 开始");
        checkTmorrowService.updateCheckTmorrow();
        log.info("每天6点检查今天是否需要抢票 结束");
    }

    @Scheduled(cron = "0 45 11,19 * * ?")
    public void checkTokenFlag(){
        log.info("每天九点四十五修改用户token字段并推送消息， 开始");
        metroService.updateTokenFlag();
        log.info("每天九点四十五修改用户token字段并推送消息 结束");
    }

////    @Scheduled(cron = "0 0 10 * * ?")
//    public void getSubwayOrder(){
//        log.info("每天十点进站后自动预约， 开始");
//        metroService.getSubwayOrder();
//        log.info("每天十点进站后自动预约 结束");
//    }

    /** 进站后自动预约 */
    @Scheduled(cron = "0 0/1 7,8 * * ? ")
    public void getSubwayByMinute(){
        metroService.getSubwayByMinute();
    }
    /** 进站后自动预约 */
    @Scheduled(cron = "0 0,10,20,30,45 9 * * ? ")
    public void getSubwayByMinute1(){
        metroService.getSubwayByMinute();
    }

    /** 进站后自动预约 */
    @Scheduled(cron = "0 30 10,11 * * ? ")
    public void getSubwayByMinute2(){
        metroService.getSubwayByMinute();
    }

    @Scheduled(cron = "0 15,45 * * * ? ")
    public void csdnRefresh(){
        csdnService.csdnRefresh();
    }

    /** 阿里云签到 */
//    @Scheduled(cron = "0 30 21 * * ?")
    public void aliyunCheckin(){
        log.info("每天晚上九点三十阿里云签到， 开始");
        aliyunCheckinService.aliyunCheckin();
        log.info("每天晚上九点三十阿里云签到， 结束");
    }
}
