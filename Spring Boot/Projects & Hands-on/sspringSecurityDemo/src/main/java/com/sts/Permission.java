package com.sts;

public enum Permission {
	 READ("student:read"),
	 WRITE("student:write");
	 
	private String permission;
	 Permission(String permissin){
		 this.permission=permissin;
	 }
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	 
	 
}
