package com.sts;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class SearchController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;
	
	@GetMapping("/search/{searchQuery}")
	public ResponseEntity<?> search(@PathVariable("searchQuery") String query, Principal principal){
		User user=this.userRepository.getUserByUsername(principal.getName());
		List<Contact> contacts=this.contactRepository.findByNameContainingAndUsers(query, user);
		return ResponseEntity.ok(contacts);
	}
}