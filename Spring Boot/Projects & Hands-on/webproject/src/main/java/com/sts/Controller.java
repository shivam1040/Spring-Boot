package com.sts;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;

@ComponentScan(basePackages = "com.sts") //this is necessary when a controller package is not defined
@org.springframework.stereotype.Controller
public class Controller {
	
	@RequestMapping("/") //to render jsp enable tomcat jasper in pom xml and prefix suffix in application.properties 
	public String home() {
		System.out.println("a");
		 return "home";
	}
}
