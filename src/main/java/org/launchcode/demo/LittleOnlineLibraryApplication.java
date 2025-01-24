package org.launchcode.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.core.SpringVersion;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
//@EnableJpaRepositories(basePackages =  "org.launchcode.demo.data", entityManagerFactoryRef="emf")

@SpringBootApplication
public class LittleOnlineLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LittleOnlineLibraryApplication.class, args);
	}

}

