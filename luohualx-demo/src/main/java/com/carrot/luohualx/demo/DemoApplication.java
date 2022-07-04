package com.carrot.luohualx.demo;

import com.carrot.luohualx.init.LuohualxInitContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 需要实现 LuohualxInitContainer 接口
 */
@SpringBootApplication
public class DemoApplication implements LuohualxInitContainer {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


}
