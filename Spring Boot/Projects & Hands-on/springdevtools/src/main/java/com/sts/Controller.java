package com.sts;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
public class Controller {
	
	@RequestMapping("/")
	@ResponseBody
	public String test() {
		return "hello";
	}
}
