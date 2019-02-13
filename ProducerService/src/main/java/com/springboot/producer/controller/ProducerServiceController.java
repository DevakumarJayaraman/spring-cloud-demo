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

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;
import com.springboot.producer.application.ProducerServiceConfig;

@RestController
@RequestMapping(value = "/ProducerService")
public class ProducerServiceController {

	@Autowired
	private EurekaClient eurekaClient;

	@Autowired
	private ProducerServiceConfig producerServiceConfig;

	private static final Logger logger = LoggerFactory.getLogger(ProducerServiceController.class);
	
	@RequestMapping(value = "/getMessage", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getMessage() {
		Map<String, Object> outputMap = new HashMap<>();
		logger.info("getMessage()");
		outputMap.put("status", "sucess");
		outputMap.put("message", producerServiceConfig.getMessage());
		return outputMap;
	}

	@RequestMapping(value = "/getApplications", method = RequestMethod.GET)
	public @ResponseBody Applications getApplications() {
		return eurekaClient.getApplications();
	}
}