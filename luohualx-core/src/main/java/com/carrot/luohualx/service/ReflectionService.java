package com.carrot.luohualx.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;

/**
 * @author carrot
 */
@Service
@Slf4j
public class ReflectionService implements ApplicationContextAware {
    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext application) throws BeansException {
        applicationContext = application;
    }
//    @Autowired
//    public  void setApplicationContext(ApplicationContext applicationContext) {
//        ReflectionService.applicationContext = applicationContext;
//    }



}
