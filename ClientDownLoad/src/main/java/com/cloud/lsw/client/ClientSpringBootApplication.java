package com.cloud.lsw.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lisw
 * @create 2021/4/16 23:14
 */
@SpringBootApplication()
@ComponentScan("com.cloud.lsw.client.controller")
public class ClientSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientSpringBootApplication.class, args);
    }
}
