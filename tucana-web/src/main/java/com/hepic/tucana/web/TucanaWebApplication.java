package com.hepic.tucana.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hepic.tucana"})
@EntityScan(basePackages={"com.hepic.tucana.dal.entity"})
@EnableJpaRepositories(basePackages = {"com.hepic.tucana.dal.dao"})
public class TucanaWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TucanaWebApplication.class, args);
    }
}
