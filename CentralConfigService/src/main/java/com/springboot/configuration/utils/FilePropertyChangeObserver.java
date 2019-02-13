package com.springboot.configuration.utils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 
 * @author jayarade
 *
 */
@Component
@Profile("native")
public class FilePropertyChangeObserver implements PropertyChangeObserver {

	private static final Logger logger = LoggerFactory.getLogger(FilePropertyChangeObserver.class);

	@Autowired
	private PropertyChangeEventPublisher eventPublisher;

	@PostConstruct
	public void observe() {
		Path filePath = Paths.get("F:/my_workspace/properties/");

		WatchService watchService;
		try {
			watchService = FileSystems.getDefault().newWatchService();
			// listen for create ,delete and modify event kinds
			filePath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					WatchKey key;
					try {
						// return signaled key, meaning events occurred on the object
						key = watchService.take();
					} catch (InterruptedException ex) {
						return;
					}
					// retrieve all the accumulated events
					for (WatchEvent<?> event : key.pollEvents()) {
						WatchEvent.Kind<?> kind = event.kind();

						logger.info("kind " + kind.name());
						Path path = (Path) event.context();
						logger.info(path.toString());

						if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
							String fileName = path.toString().replace(".properties", "");
							if ("application".equals(fileName)) {
								eventPublisher.publishEvent("#");
							} else {
								String str[] = fileName.split("-");
								eventPublisher.publishEvent(str[0]);
							}
						}
					}
					key.reset();
				}
			}
		}).start();
	}
}