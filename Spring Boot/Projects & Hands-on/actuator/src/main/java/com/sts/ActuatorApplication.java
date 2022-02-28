package com.sts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActuatorApplication {
//actuator is for managing and monitoring our application in production stage
	public static void main(String[] args) {
		SpringApplication.run(ActuatorApplication.class, args);
	}

}
