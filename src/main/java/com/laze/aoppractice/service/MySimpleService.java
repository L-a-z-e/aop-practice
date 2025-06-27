package com.laze.aoppractice.service;

import org.springframework.stereotype.Service;

@Service
public class MySimpleService {
    /**
     * 간단한 메시지를 반환하는 메소드
     * @param message
     * @return
     */
    public String doSomething(String message) {
        System.out.println("MySimpleService.doSomething() called with message : " + message);
        return "Processed: " + message;
    }

    /**
     * 실행 시간이 오래 걸리는 작업을 시뮬레이션하는 메소드
     */
    public void doSomethingHeavy() {
        System.out.println("MySimpleService.doSomethingHeavy() started...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("MySimpleService.doSomethingHeavy() finished.");
    }
}