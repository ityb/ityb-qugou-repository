package com.ityb.qugou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.ityb.qugou.*.mapper")
public class SearchAppliction {
	public static void main(String[] args) {
		SpringApplication.run(SearchAppliction.class, args);
	}
}
