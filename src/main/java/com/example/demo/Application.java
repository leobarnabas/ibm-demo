package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import static com.example.demo.constants.AppConstants.TOPIC_BALANCE;
import static com.example.demo.constants.AppConstants.TOPIC_TRANSACTION;

@SpringBootApplication

public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
