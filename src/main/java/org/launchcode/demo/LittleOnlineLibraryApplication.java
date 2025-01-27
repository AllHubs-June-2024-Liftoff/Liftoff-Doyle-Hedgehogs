package org.launchcode.demo;

import org.launchcode.demo.models.ApiActions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
//@EnableJpaRepositories(basePackages =  "org.launchcode.demo.data", entityManagerFactoryRef="emf")

@SpringBootApplication /*(exclude = {DataSourceAutoConfiguration.class })*/
public class LittleOnlineLibraryApplication {

	public static void main(String[] args) {SpringApplication.run(LittleOnlineLibraryApplication.class, args);

		try{
		String search = ApiActions.ApiSearch("Dune");
		System.out.print(ApiActions.ParseResults(search));

		} catch (IOException ignored){
			System.out.println("OOPS");
		}
	}
}

