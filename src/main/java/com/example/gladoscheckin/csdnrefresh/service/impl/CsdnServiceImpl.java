package com.example.gladoscheckin.csdnrefresh.service.impl;

import com.example.gladoscheckin.csdnrefresh.service.CsdnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.csdnrefresh.service.impl
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2023/6/818:03
 */
@Service
@Slf4j
public class CsdnServiceImpl implements CsdnService {

    @Autowired
    private JsoupGetIp jsoupGetIp;

    @Override
    public void csdnRefresh() {
        jsoupGetIp.csdnRefresh();
    }
}
