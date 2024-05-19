package com.spring.security.dto;

public class JwtResponse {

	private String accessToken;

	private String token;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public JwtResponse(String accessToken, String token) {
		super();
		this.accessToken = accessToken;
		this.token = token;
	}

	public JwtResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

}
