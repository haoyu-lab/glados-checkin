package com.example.gladoscheckin.csdnrefresh.controller;

import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.csdnrefresh.service.CsdnService;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsdnController {

    @Autowired
    private CsdnService csdnService;

    @GetMapping("/csdnRefresh")
    public AjaxResult csdnRefresh(){
        csdnService.csdnRefresh();
        return AjaxResult.build2Success("csdn刷新完成");
    }
}
