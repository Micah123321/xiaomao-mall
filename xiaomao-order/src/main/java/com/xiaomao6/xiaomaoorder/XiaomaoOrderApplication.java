package com.xiaomao6.xiaomaoorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaomao6.xiaomaoorder.dao")
public class XiaomaoOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaomaoOrderApplication.class, args);
    }

}
