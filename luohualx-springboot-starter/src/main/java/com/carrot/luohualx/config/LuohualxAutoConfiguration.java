package com.carrot.luohualx.config;

import com.carrot.luohualx.service.LuohualxService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author carrot
 * @date 2022/7/4
 */
@Configuration
@EnableConfigurationProperties(LuohualxProperties.class)
@ConditionalOnProperty(prefix = "luohualx",matchIfMissing = true,value = "true")
public class LuohualxAutoConfiguration {
    @Resource
    private LuohualxProperties luohualxProperties;

    @Bean
    @ConditionalOnMissingBean(LuohualxService.class)
    public LuohualxService luohualxService() {
        return luohualxProperties.isEnable() ? new LuohualxService() : null;
    }

}
