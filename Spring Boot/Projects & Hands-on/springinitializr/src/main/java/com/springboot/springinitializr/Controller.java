package com.springboot.springinitializr;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
@ResponseBody //this will send the returning string as dom body not jsp page
public class Controller {
	
	@RequestMapping("/")
	public String first() {
		return "first";
	}
}
