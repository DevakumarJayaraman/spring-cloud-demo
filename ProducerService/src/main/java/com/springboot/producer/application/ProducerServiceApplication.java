package com.springboot.producer.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages="com.springboot.*")
public class ProducerServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProducerServiceApplication.class, args);
	}
}