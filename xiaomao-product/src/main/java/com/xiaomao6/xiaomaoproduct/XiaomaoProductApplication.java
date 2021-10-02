package com.xiaomao6.xiaomaoproduct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableFeignClients(basePackages = "com.xiaomao6.xiaomaoproduct.feign")
@MapperScan("com.xiaomao6.xiaomaoproduct.dao")
public class XiaomaoProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaomaoProductApplication.class, args);
    }

}
