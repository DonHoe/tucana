package com.hepic.tucana.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hepic.tucana"})
@MapperScan("com.hepic.tucana.dal")
@EnableScheduling
public class TucanaWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TucanaWebApplication.class, args);
    }
}
