package com.springboot.consumer.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RefreshScope
public class ConsumerServiceConfig {
	
	@Value("${producer.getMessage.url}")
	private String producerGetMessageUrl;
	
	@Value("${zuul.server.app.name}")
	private String zuulServerAppName;
	
	@Value("${message}")
	private String message;
	
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	public String getProducerGetMessageUrl() {
		return producerGetMessageUrl;
	}

	public String getZuulServerAppName() {
		return zuulServerAppName;
	}

	public String getMessage() {
		return message;
	}
}