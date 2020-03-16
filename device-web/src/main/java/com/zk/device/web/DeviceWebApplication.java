package com.zk.device.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zk.device"})
@MapperScan("com.zk.device.dal")
public class DeviceWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeviceWebApplication.class, args);
    }
}
