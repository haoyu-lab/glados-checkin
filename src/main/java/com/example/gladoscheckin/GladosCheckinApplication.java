package com.example.gladoscheckin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.example.gladoscheckin.*")
@MapperScan("com.example.gladoscheckin.*.mapper")
public class GladosCheckinApplication {

    public static void main(String[] args) {
        SpringApplication.run(GladosCheckinApplication.class, args);
    }

}
