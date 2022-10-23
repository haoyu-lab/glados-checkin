package com.example.gladoscheckin.metro.service.impl;

import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.metro.MetroVO;
import com.example.gladoscheckin.metro.service.MetroService;
import com.example.gladoscheckin.metro.service.TaskUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetroServiceImpl implements MetroService {

    @Autowired
    TaskUtils taskUtils;

    @Override
    public AjaxResult metroCheckin() {
        String authorization = "YTliYmQzNDQtMzE5Zi00NjcxLTgyYTMtZmQwNzY5YzA5ZTk2LDE2NjcyMDk2NzE1MDMsQ2QvcnlMYVZaZS8yQ0VQY2NlaFZ1NGM0U2MwPQ==";
        String time = "0800-0810";
        MetroVO metroVO = MetroVO.builder().authorization(authorization)
                .time(time).build();
        /** 检查今天是否需要抢票 */
        taskUtils.checkTomorrowIsHoliday(metroVO);
        /** 检查token是否过期 */
        Boolean aBoolean = taskUtils.checkToken(metroVO);
        if(aBoolean){
            taskUtils.startReservation(metroVO);
        }

        return AjaxResult.build2Success(true);
    }
}
