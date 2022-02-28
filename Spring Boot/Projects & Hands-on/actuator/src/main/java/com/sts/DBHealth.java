package com.sts;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Controller;

@Controller
public class DBHealth implements HealthIndicator{

	public boolean checkHealthGood() { //we can write custom logic here related to DB to check its health
		return true;
	}
	@Override
	public Health health() {
		if(checkHealthGood()) {
			return Health.up().withDetail("DBService", "Service Running").build(); //returns a key and value message when health check is good, and this message can be accessed by means of actuator urls/endpoints
		}
		return Health.down().withDetail("DBService", "Service not Running").build();
	}
}
