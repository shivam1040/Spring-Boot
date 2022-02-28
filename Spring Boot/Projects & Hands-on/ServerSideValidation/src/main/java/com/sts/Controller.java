package com.sts;

import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@org.springframework.stereotype.Controller
public class Controller {
	
	@GetMapping("/form") //entering data in form
	public String form(Model m) {
		m.addAttribute("loginData", new LoginData()); //sending a dummy obj to form for storing data
		return "form";
	}
	
	@PostMapping("/submit") //action post form submit
	public String submit(@Valid @ModelAttribute("loginData") LoginData loginData, BindingResult result){ //to validate a bean, declare Valid annotion in starting and that result gets stored in bindingResult which is declared later
		if(result.hasErrors()) { //if validation error then display
			System.out.println(result);
			return "form";
		}
		System.out.println(loginData);
		return "submit";
	}
}