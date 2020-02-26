package com.github.texhxcho.rabbitmp.mqtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MqtestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqtestApplication.class, args);
    }

}
