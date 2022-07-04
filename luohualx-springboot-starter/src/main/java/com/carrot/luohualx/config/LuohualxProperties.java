package com.carrot.luohualx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author carrot
 * @date 2022/7/1
 */
@ConfigurationProperties(prefix = "luohualx")
@EnableConfigurationProperties
@Data
public class LuohualxProperties {
    private String name = "luohualx";
    private boolean enable = true;

}
