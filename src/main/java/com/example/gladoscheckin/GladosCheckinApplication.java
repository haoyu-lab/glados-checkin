package com.example.gladoscheckin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GladosCheckinApplication {

    public static void main(String[] args) {
        SpringApplication.run(GladosCheckinApplication.class, args);
    }

}
