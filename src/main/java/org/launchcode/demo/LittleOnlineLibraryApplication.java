package org.launchcode.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class LittleOnlineLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LittleOnlineLibraryApplication.class, args);
	}

}
