package com.sts;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginData {
	@NotBlank(message = "cant be empty") //this annotaion comes from bean validation dependency, used to validate bean as per our conditons, do check pom xml for dependencies
	@Size(min = 2, max = 4, message = "not in size range")
	private String username;
	
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "invalid")
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "LoginData [username=" + username + ", email=" + email + "]";
	}
	
	
}
