package com.springboot.configuration.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("jdbc")
public class DBPropertyChangeObserver implements PropertyChangeObserver {

	private static final Logger logger = LoggerFactory.getLogger(FilePropertyChangeObserver.class);

	@Autowired
	private PropertyChangeEventPublisher eventPublisher;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Scheduled(fixedDelay = 60 * 1000)
	@Override
	public void observe() {
		
		try {
			System.out.println(System.getProperty("spring.datasource.platform"));
			logger.info("Checking for config changes...");
			List<String> appsUpdated = jdbcTemplate.queryForList(
					"SELECT DISTINCT APPLICATION FROM APP_CONFIG_CHANGE WHERE STATUS='NEW'", String.class);
			logger.info("Apps Updated : {}", appsUpdated);
			appsUpdated.forEach(app -> {
				eventPublisher.publishEvent(app);
				jdbcTemplate.update("UPDATE APP_CONFIG_CHANGE SET STATUS='Read' WHERE APPLICATION='" + app + "'");
			});
		} catch (Exception ex) {
			logger.error("Exception Occurred : {}", ex);
		}
	}
}