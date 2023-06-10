package com.example.gladoscheckin.csdnrefresh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gladoscheckin.csdnrefresh.mapper.CsdnDetailMapper;
import com.example.gladoscheckin.csdnrefresh.pojo.CsdnDetail;
import com.example.gladoscheckin.csdnrefresh.service.CsdnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.csdnrefresh.service.impl
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2023/6/818:03
 */
@Service
@Slf4j
public class CsdnServiceImpl extends ServiceImpl<CsdnDetailMapper, CsdnDetail> implements CsdnService {

    @Autowired
    private JsoupGetIp jsoupGetIp;
    @Autowired
    @Qualifier("asyncTaskExecutor")
    private ThreadPoolTaskExecutor asyncTaskExecutor;

    @Override
    public void csdnRefresh() {
        List<CsdnDetail> csdnDetails = baseMapper.selectList(null);
        if(csdnDetails.size() != 0){
            CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
                jsoupGetIp.csdnRefresh(csdnDetails);
            },asyncTaskExecutor);
        }
    }
}
