package com.github.texhxcho.rabbitmp.mqtest;

import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(byte[] messageBodyBytes) {
        log.info("Received <" + new String(messageBodyBytes) + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
