package com.spring.security.dto;

public class RefreshTokenRequest {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public RefreshTokenRequest(String token) {
		super();
		this.token = token;
	}

	public RefreshTokenRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

}
