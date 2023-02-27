package com.example.gladoscheckin.metro.service;

import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.metro.Metror;
import com.example.gladoscheckin.pushsend.pojo.VICode;

/**
 *
 */
public interface MetroService {

    AjaxResult metroCheckin();
    AjaxResult metroCheckin1();

    AjaxResult searchMetro();

    void refreshIsNeedOrder();

    void initializeIsNeedOrder();

    void updateMetror(Metror metror);

    AjaxResult getVlCode(String phone);

    AjaxResult metorLogin(VICode viCode);

    void updateTokenFlag();
}
