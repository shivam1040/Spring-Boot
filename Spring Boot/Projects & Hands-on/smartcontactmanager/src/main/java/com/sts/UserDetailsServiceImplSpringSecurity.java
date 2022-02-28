package com.sts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//third stepin initiallizing spring security
public class UserDetailsServiceImplSpringSecurity implements UserDetailsService {

	@Autowired
	private UserRepository repository;	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=repository.getUserByUsername(username);
		
		if(user==null)
			throw new UsernameNotFoundException("not found user");
		UserDetails userDetails=new CustomUserDetailsSpringSecurity(user);
		return userDetails;
	}
}
