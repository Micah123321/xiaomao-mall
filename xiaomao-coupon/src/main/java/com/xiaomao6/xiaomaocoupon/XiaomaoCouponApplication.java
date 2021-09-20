package com.xiaomao6.xiaomaocoupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.xiaomao6.xiaomaocoupon.dao")
public class XiaomaoCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaomaoCouponApplication.class, args);
    }

}
