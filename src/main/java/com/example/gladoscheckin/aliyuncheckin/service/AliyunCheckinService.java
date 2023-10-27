package com.example.gladoscheckin.aliyuncheckin.service;

import com.example.gladoscheckin.aliyuncheckin.pojo.AliyunCheckin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gladoscheckin.common.AjaxResult;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hhy
 * @since 2023-10-27
 */
public interface AliyunCheckinService extends IService<AliyunCheckin> {

    AjaxResult aliyunCheckin();
}
