package com.example.gladoscheckin.csdnrefresh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gladoscheckin.csdnrefresh.pojo.CsdnDetail;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.csdnrefresh.service
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2023/6/818:03
 */
public interface CsdnService extends IService<CsdnDetail> {

    void csdnRefresh();
}
