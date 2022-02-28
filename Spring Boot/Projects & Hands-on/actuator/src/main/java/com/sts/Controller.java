package com.sts;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	@Autowired
	private Student student;
	
	@GetMapping("/get")
	public Map<String, String> get(){
		return Map.of("Name", "SJ"); //this will send json obj in response
	}
}
