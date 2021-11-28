package com.monsters.botcore;

import love.forte.simbot.spring.autoconfigure.EnableSimbot;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableSimbot
@EnableAsync
@SpringBootApplication
public class BotCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotCoreApplication.class, args);
    }

}
