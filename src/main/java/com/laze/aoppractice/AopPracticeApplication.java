package com.laze.aoppractice;

import com.laze.aoppractice.service.MySimpleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AopPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopPracticeApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(MySimpleService service) {
        return args -> {
            System.out.println("\n==== Executing Service Methods via CommandLineRunner ====");
            service.doSomething("Hello AOP!");
            System.out.println("--------------------------------");
            service.doSomethingHeavy();
            System.out.println("==== Finished Executing Service Methods ====\n");
        };
    }
}
