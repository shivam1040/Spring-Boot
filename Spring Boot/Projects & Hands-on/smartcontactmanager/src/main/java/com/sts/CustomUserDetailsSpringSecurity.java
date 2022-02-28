package com.sts;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//second step spring security
public class CustomUserDetailsSpringSecurity implements UserDetails {

	private User user;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { //this specifiesnthe list of rights given to user logging in
		// TODO Auto-generated method stub
		
		SimpleGrantedAuthority simpleGrantedAuthority= new SimpleGrantedAuthority(user.getRole());
		return List.of(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public CustomUserDetailsSpringSecurity(User user) {
		super();
		this.user = user;
	}
}
