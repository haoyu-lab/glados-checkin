package com.example.gladoscheckin.metro.service;

import com.example.gladoscheckin.common.AjaxResult;

/**
 *
 */
public interface MetroService {

    AjaxResult metroCheckin();

    AjaxResult searchMetro();

    void refreshIsNeedOrder();

    void initializeIsNeedOrder();
}
