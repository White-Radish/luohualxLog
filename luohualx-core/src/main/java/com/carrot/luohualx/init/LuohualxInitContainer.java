package com.carrot.luohualx.init;


import com.carrot.luohualx.service.ReflectionService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @author carrot
 * @date 2022/3/10
 * 刷新spring容器接口
 */

@Service
public interface LuohualxInitContainer extends ApplicationListener<ContextRefreshedEvent> {


    @Override
    default void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            ReflectionService.applicationContext = contextRefreshedEvent.getApplicationContext();
        }
    }


}