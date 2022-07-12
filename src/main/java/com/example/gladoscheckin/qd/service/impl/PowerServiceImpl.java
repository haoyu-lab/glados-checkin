package com.example.gladoscheckin.qd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gladoscheckin.common.AjaxResult;
import com.example.gladoscheckin.common.Status;
import com.example.gladoscheckin.qd.mapper.PowerMapper;
import com.example.gladoscheckin.qd.pojo.Power;
import com.example.gladoscheckin.qd.service.PowerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class PowerServiceImpl extends ServiceImpl<PowerMapper,Power> implements PowerService {

    @Override
    public AjaxResult insertPower(Power power) {
        if(StringUtils.isEmpty(power.getEmail()) || StringUtils.isEmpty(power.getCookie())){
            return AjaxResult.build(Status.SERVER_ERROR,"邮箱或cookie不可为空","邮箱或cookie不可为空");
        }
        baseMapper.insert(power);
        log.info("新增的用户:{}",power);
        return AjaxResult.build2Success(true);
    }

    @Override
    public List<Power> selectPower() {
        QueryWrapper<Power> queryWrapper = new QueryWrapper<>();
        List<Power> powerList = baseMapper.selectList(queryWrapper);
        log.info("获取到的用户:{}",powerList);
        return powerList;
    }

}
