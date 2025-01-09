package org.launchcode.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.IOException;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
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
