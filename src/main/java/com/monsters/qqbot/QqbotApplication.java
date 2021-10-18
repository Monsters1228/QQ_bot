package com.monsters.qqbot;

import love.forte.simbot.spring.autoconfigure.EnableSimbot;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan("com.monsters.qqbot.mapper")
@EnableSimbot
@EnableAsync
@SpringBootApplication
public class QqbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(QqbotApplication.class, args);
    }

}
