package com.sts;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(username.equals("admin")){
			return new User("admin", "admin", new ArrayList<>()); //admin 1 is username, admin 2 is password, collecion param is for sending list of permission
		}
		else {
			throw new UsernameNotFoundException("not found");
		}
		//return null;
	}

}
