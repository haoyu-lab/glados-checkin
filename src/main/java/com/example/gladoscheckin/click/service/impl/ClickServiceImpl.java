package com.example.gladoscheckin.click.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gladoscheckin.click.Click;
import com.example.gladoscheckin.click.mapper.ClickMapper;
import com.example.gladoscheckin.click.service.ClickService;
import com.example.gladoscheckin.common.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author houhaoyu
 * @title: com.example.gladoscheckin.click.service.impl
 * @projectName glados-checkin1
 * @description: TODO
 * @date 2023/2/2014:26
 */
@Service
@Slf4j
public class ClickServiceImpl  extends ServiceImpl<ClickMapper, Click> implements ClickService {


    @Override
    public AjaxResult getClicks() {
        List<Click> clicks = baseMapper.selectList(new QueryWrapper<Click>());
        Click click = null;
        if(CollectionUtils.isEmpty(clicks)){
            click = Click.builder().clicksCount(String.valueOf(1L)).build();
            baseMapper.insert(click);
        }else{
            click = clicks.get(0);
            click.setClicksCount(String.valueOf(Long.valueOf(click.getClicksCount())+1L));
            baseMapper.update(click,new QueryWrapper<Click>());
        }
//        this.saveOrUpdate(click);
        return AjaxResult.build2Success(true);
    }
}
