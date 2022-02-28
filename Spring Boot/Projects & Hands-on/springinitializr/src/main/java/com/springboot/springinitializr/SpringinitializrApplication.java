package com.springboot.springinitializr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//this class is starting point for application
@SpringBootApplication //this is for object creting and equivalent to @Configuration, @EnableConfiguration, @ComponentScan which we use in spring for object creation
public class SpringinitializrApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringinitializrApplication.class, args);
	}

}

//go through th project direcory and you'd find application.properties in resources folder where we define properties related to server, DB etc.