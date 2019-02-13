package com.springboot.producer.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class ProducerServiceConfig {
	
	@Value("${message}")
	private String message;

	public String getMessage() {
		return message;
	}	
}