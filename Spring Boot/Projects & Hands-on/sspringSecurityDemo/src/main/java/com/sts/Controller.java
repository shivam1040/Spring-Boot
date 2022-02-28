package com.sts;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
public class Controller {
	
	private List<Student> students=Arrays.asList(new Student(1));
	@GetMapping("/")
	@ResponseBody
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PreAuthorize("hasAuthority('student:read')") //annotation method to limit handler to role/permission, value comes from enum
	public String getStudent() {
		return students.toString();
	}
}
