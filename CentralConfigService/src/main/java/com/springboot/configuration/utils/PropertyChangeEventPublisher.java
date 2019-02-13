package com.springboot.configuration.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.SpringCloudBusClient;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(SpringCloudBusClient.class)
public class PropertyChangeEventPublisher {

	private static final Logger logger = LoggerFactory.getLogger(PropertyChangeEventPublisher.class);

	@Autowired
	private SpringCloudBusClient cloudBusClient;

	public void publishEvent(String destinationApplication) {
		logger.info("publishEvent for application : {}", destinationApplication);
		cloudBusClient
				.springCloudBusOutput().send(
						MessageBuilder
								.withPayload(new RefreshRemoteApplicationEvent("Origin", "configService",
										"application".equals(destinationApplication) ? "*" : destinationApplication))
								.build());
	}
}