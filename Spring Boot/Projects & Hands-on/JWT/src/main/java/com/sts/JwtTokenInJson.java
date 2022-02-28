package com.sts;

public class JwtTokenInJson {
	String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public JwtTokenInJson(String token) {
		super();
		this.token = token;
	}

	public JwtTokenInJson() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "JwtTokenInJson [token=" + token + "]";
	}
	
}
