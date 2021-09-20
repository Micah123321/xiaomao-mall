package com.xiaomao6.xiaomaothirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class XiaomaoThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaomaoThirdPartyApplication.class, args);
    }

}
