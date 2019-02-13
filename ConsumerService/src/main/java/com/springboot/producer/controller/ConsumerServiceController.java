package com.springboot.producer.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;
import com.springboot.consumer.application.ConsumerServiceConfig;

@RestController
@RequestMapping(value = "/ConsumerService")
public class ConsumerServiceController {

	@Autowired
	private EurekaClient eurekaClient;

	@Autowired
	private ConsumerServiceConfig consumerServiceConfig;
	
	@Autowired
	private RestTemplate restTemplate;

	private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceController.class);
	
	@RequestMapping(value = "/getMessage", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getMessage() {
		Map<String, Object> outputMap = new HashMap<>();
		logger.info("getMessage()");
		outputMap.put("status", "sucess");
		outputMap.put("message", consumerServiceConfig.getMessage());
		return outputMap;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/executeRemote", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> executeRemote() {
		Map<String, Object> response = new HashMap<>();
		try {
			logger.info("Starting  - executeRemote");
			logger.info(" ProducerService get Message URL : {}", consumerServiceConfig.getProducerGetMessageUrl());

			Map<String, Object> output = restTemplate.getForObject("http://"
					+ consumerServiceConfig.getZuulServerAppName() + consumerServiceConfig.getProducerGetMessageUrl(),
					Map.class);
			logger.info("Response Recevied from ProducerService : {}", output);
			logger.info("Completing  - executeRemote");
			return output;
		} catch (Exception ex) {
			logger.error("Exception Occurred : {}", ex);
			response.put("status", "failed");
			response.put("reason", ex.getMessage());
			return response;
		}
	}

	@RequestMapping(value = "/getApplications", method = RequestMethod.GET)
	public @ResponseBody Applications getApplications() {
		return eurekaClient.getApplications();
	}
}