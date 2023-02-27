package com.example.gladoscheckin.metro.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gladoscheckin.click.Click;
import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.metro.CheckTmorrow;
import com.example.gladoscheckin.metro.Metror;
import com.example.gladoscheckin.metro.mapper.CheckTmorrowMapper;
import com.example.gladoscheckin.metro.mapper.MetrorMapper;
import com.example.gladoscheckin.metro.service.CheckTmorrowService;
import com.example.gladoscheckin.metro.service.MetroService;
import com.example.gladoscheckin.metro.service.TaskUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.metro.service.impl
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2023/2/2710:13
 */
@Service
@Slf4j
public class CheckTmorrowServiceImpl  extends ServiceImpl<CheckTmorrowMapper, CheckTmorrow> implements CheckTmorrowService {

    @Autowired
    TaskUtils taskUtils;

    @Override
    public void updateCheckTmorrow() {
        List<CheckTmorrow> checkTmorrows = baseMapper.selectList(new QueryWrapper<CheckTmorrow>());
        CheckTmorrow checkTmorrow = null;
        Boolean isReservation = taskUtils.checkTomorrowIsHoliday();
        if(CollectionUtils.isEmpty(checkTmorrows)){

            if (isReservation) {
                checkTmorrow = CheckTmorrow.builder().tomorrowIsFlag("Y").build();
            } else {
                checkTmorrow = CheckTmorrow.builder().tomorrowIsFlag("N").build();
            }
            baseMapper.insert(checkTmorrow);
        }else{
            checkTmorrow = checkTmorrows.get(0);
            if (isReservation) {
                checkTmorrow.setTomorrowIsFlag("Y");
            }else {
                checkTmorrow.setTomorrowIsFlag("N");
            }
            baseMapper.update(checkTmorrow,new QueryWrapper<CheckTmorrow>());
        }
    }

    @Override
    public CheckTmorrow getCheckTmorrow() {
        List<CheckTmorrow> checkTmorrows = baseMapper.selectList(new QueryWrapper<CheckTmorrow>());
        if(!CollectionUtils.isEmpty(checkTmorrows) && checkTmorrows.size() == 1){
            return checkTmorrows.get(0);
        }else {
            return null;
        }
    }
}
