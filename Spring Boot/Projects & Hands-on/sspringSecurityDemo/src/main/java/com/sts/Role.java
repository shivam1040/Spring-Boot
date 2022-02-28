package com.sts;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

public enum Role {
	STUDENT(Sets.newHashSet()),
	ADMIN(Sets.newHashSet(Permission.READ, Permission.WRITE)); //sets used to ensure that no repititive enums from Permission enum are given)
	
	private Set<Permission> permission; //creating set collection obj of permission enum type
	
	Role(Set<Permission> permission){
		this.permission=permission;
	}

	public Set<Permission> getPermission() {
		return permission;
	}

	public void setPermission(Set<Permission> permission) {
		this.permission = permission;
	}
	
	public Set<SimpleGrantedAuthority> getGrantedAuthorities(){ //this methods associates permissions with related role as per given in enum
		Set<SimpleGrantedAuthority> permissions=getPermission().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
		permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name())); //this is necessary to assign role with permission as per convention
		return permissions;
	}
}