package com.example.gladoscheckin.timingtask.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author houhaoyu
 * @title: com.bfjz.wmannouncement.modules.timer.common
 * @projectName wealthmanagement
 * @description: TODO
 * @date 2023/3/1616:50
 */
@Component
public class MyBeanUtil implements ApplicationContextAware {

    protected static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext app) throws BeansException {
        if (applicationContext == null) {
            applicationContext = app;
        }
    }
    /**
     * 通过类的class从容器中手动获取对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
