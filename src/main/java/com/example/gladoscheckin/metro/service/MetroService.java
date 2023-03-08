package com.example.gladoscheckin.metro.service;

import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.metro.Metror;
import com.example.gladoscheckin.metro.RequestVO;
import com.example.gladoscheckin.pushsend.pojo.VICode;

/**
 *
 */
public interface MetroService {

    AjaxResult metroCheckin() throws Exception;
    AjaxResult metroCheckin1();

    AjaxResult searchMetro();

    void refreshIsNeedOrder();

    void initializeIsNeedOrder();

    void updateMetror(Metror metror);

    AjaxResult getVlCode(String phone);

    AjaxResult metorLogin(VICode viCode);

    AjaxResult updateTokenFlag();

    AjaxResult insertOrUpdateMetor(RequestVO requestVO);
}
