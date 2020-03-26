package com.ityb.qugou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.ityb.qugou.base.mapper")
@EnableFeignClients //启动feign模式的消费数据
public class MerchantApplication {
	public static void main(String[] args) {
		SpringApplication.run(MerchantApplication.class, args);
	}
}

