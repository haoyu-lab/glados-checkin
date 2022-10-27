package com.example.gladoscheckin.metro.service;

import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.metro.Metror;

/**
 *
 */
public interface MetroService {

    AjaxResult metroCheckin();

    AjaxResult searchMetro();

    void refreshIsNeedOrder();

    void initializeIsNeedOrder();

    void updateMetror(Metror metror);
}
