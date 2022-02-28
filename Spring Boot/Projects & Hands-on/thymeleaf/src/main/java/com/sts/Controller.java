package com.sts;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class Controller {
	
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("name", "SJ");
		return "about"; //this will return html prefix since nothing has been defined for prefix suffix in application.properties, and about.html is stored in tempelate folder of resources
	}
	
	@GetMapping("/iterate") //example for thymeleaf iterate
	public String iterate(Model m) {
		List<String> name=List.of("A", "B", "C");
		m.addAttribute("list", name);
		return "iterate";
	}
	
	@GetMapping("/conditional") //thymeleaf demo for conditional instrucions
	public String conditional(Model m) {
		m.addAttribute("active", true);
		List<Integer> list=List.of(1, 2, 3, 4);
		m.addAttribute("list", list);
		return "conditional";
	}
	
	@GetMapping("/include")
	public String include(Model m) {
		m.addAttribute("name", "SJ");
		return "includeTempelate";
	}
	
	@GetMapping("/inherit") //thymeleaf demo for inheritance
	public String inheritance() {
		return "inheritBase";
	}
	
	@RequestMapping("/add")
	public String add() { //thymeleaf demo to add css, js content in html
		return "addCSSJS";
	}
}