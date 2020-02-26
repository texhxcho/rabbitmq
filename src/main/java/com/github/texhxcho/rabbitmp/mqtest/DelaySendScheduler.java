package com.github.texhxcho.rabbitmp.mqtest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DelaySendScheduler {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Connection conn;
    private Channel channel;

    @PostConstruct
    public void init() {
        conn = connectionFactory.createConnection();
        channel = conn.createChannel(false);

        try {
            // x-delayed-message 타입의 exchange 생성
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("x-delayed-type", "direct");
            channel.exchangeDeclare(DelayRabbitMQConfig.delayExchangeName, "x-delayed-message", true, false, args);

            // queue 생성
            channel.queueDeclare(DelayRabbitMQConfig.delayQueueName, true, false, false, null);

            // delay-exchange 에 delay_test 큐 바인드
            channel.queueBind(DelayRabbitMQConfig.delayQueueName, DelayRabbitMQConfig.delayExchangeName, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void destory() {
        try {
            channel.close();
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 * * * * *")
    public void onSend() throws Exception {
        log.info("Sending message... Start");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        IntStream.range(1, 20000)
            .parallel()
            .forEach(val -> {
//                rabbitTemplate.convertAndSend(DelayRabbitMQConfig.delayQueueName, sdf.format(new Date()) + " Hello, RabbitMQ! "+val);
                delayMessage(val);
            });
        stopWatch.stop();
        log.info(stopWatch.toString());
        log.info("Sending message... End");

    }

    private void delayMessage(int val) {
        // test 용 메세지 publish. 5초 후 delay_test 큐에 들어오도록
        // x-delay 값을 5000 으로 설정
        String message = sdf.format(new Date()) + " Hello, RabbitMQ! " + val;
        byte[] messageBodyBytes = message.getBytes();
        AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder();
        HashMap<String, Object> headers = new HashMap<String, Object>();
//        headers.put("x-delay", 60 * 60 * 1000);
        headers.put("x-delay", 5000);
        props.headers(headers);
        try {
            channel.basicPublish(DelayRabbitMQConfig.delayExchangeName, "", props.build(), messageBodyBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
