package com.sceneforge;

import com.sceneforge.config.DatabaseBootstrapInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @title        SceneForgeServerApplication.java
 * @author       zhenglin.cn.cq@gmail.com
 * @description  SceneForge Java服务端入口
 * @createTime   2026-06-24 10:36:00
 */
@SpringBootApplication
@EnableScheduling
public class SceneForgeServerApplication {

    /**
     * 服务端启动入口
     *
     * @param args String[] 启动参数
     */
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SceneForgeServerApplication.class);
        application.addInitializers(new DatabaseBootstrapInitializer());
        application.run(args);
    }
}
