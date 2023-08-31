package com.example.gladoscheckin.qd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.qd.pojo.Power;

import java.util.List;

public interface PowerService extends IService<Power> {

    AjaxResult insertPower(Power power);

    List<Power> selectPower();

    List<Power> selectByIsSuccess();

    void cleanPower();
}
