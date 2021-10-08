package com.xiaomao6.xiaomaoware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableTransactionManagement
@MapperScan("com.xiaomao6.xiaomaoware.dao")
public class XiaomaoWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaomaoWareApplication.class, args);
    }

}
