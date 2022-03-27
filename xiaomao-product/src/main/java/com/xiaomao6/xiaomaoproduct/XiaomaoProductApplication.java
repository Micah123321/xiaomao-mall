package com.xiaomao6.xiaomaoproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//nacos发现
@EnableDiscoveryClient
//feign配置
@EnableFeignClients(basePackages = "com.xiaomao6.xiaomaoproduct.feign")
public class XiaomaoProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaomaoProductApplication.class, args);
    }

}
