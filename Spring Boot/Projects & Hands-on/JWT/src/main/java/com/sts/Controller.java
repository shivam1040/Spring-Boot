package com.sts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	@Autowired
	private AuthenticationManager authenticationManager; //this is required for specifying aurthentication type, do declare a bean for this in configuration class before autowiring it
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl; //necessary for loading userdetails by username provided by user
	@Autowired
	private JwtUtil jwtUtil; //this is utility class for operation related to jwt token
	
	//while return request after generating token, put the token in header in form, Authorization:Bearer token
	
	@GetMapping("/") //this response will only return when jwttoken is authenticated by filterin our case
	public String welcome() {
		return "a";
	}
	
	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest){ //jwt request class maps username password recieved from client end
		System.out.println(jwtRequest);
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword())); //authenticationg user by recieved username password from client
		} catch (UsernameNotFoundException e) {
			
		}
		
		UserDetails userDetails=this.userDetailsServiceImpl.loadUserByUsername(jwtRequest.getUsername()); //retrieving userdetails by username
		String s=this.jwtUtil.generateToken(userDetails); //generating token
		return ResponseEntity.ok(new JwtTokenInJson(s)); //returning token to client in response by converting token to json using jwtjson class
	}
}