//package com.skp.rabbitmp.mptest;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.SimpleTimeZone;
//import java.util.stream.IntStream;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StopWatch;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@Slf4j
//public class SendScheduler {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//    @Scheduled(cron = "0/20 * * * * *")
//    public void onSend() {
//        log.info("Sending message... Start");
//
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        IntStream.range(1, 150)
//            .parallel()
//            .forEach(val -> {
//                rabbitTemplate.convertAndSend(RabbitMQConfig.queueName, sdf.format(new Date()) + " Hello, RabbitMQ! "+val);
//            });
//        stopWatch.stop();
//        log.info(stopWatch.toString());
//        log.info("Sending message... End");
//    }
//
//}
