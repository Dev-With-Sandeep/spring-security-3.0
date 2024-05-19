package com.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.dto.AuthRequest;
import com.spring.security.dto.JwtResponse;
import com.spring.security.dto.RefreshTokenRequest;
import com.spring.security.entity.RefreshToken;
import com.spring.security.entity.User;
import com.spring.security.service.JwtService;
import com.spring.security.service.RefreshTokenService;
import com.spring.security.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private JwtService jwtService;

	@PostMapping("/save")
	public ResponseEntity<User> save(@RequestBody User user) {
		User savedUser = userService.save(user);
		return new ResponseEntity<User>(savedUser, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> generateToken(@RequestBody AuthRequest authRequest) {
		JwtResponse resposne = userService.authenticateAndGetToken(authRequest);
		return new ResponseEntity<JwtResponse>(resposne, HttpStatus.OK);
	}

	@PostMapping("/refresh/token")
	public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		return refreshTokenService.findByToken(refreshTokenRequest.getToken())
		.map(refreshTokenService::verifyExpiration)
		.map(RefreshToken::getUser)
		.map(user -> {
			String accessToken = jwtService.generateToken(user.getEmail());
			return new ResponseEntity<JwtResponse>(new JwtResponse(accessToken, refreshTokenRequest.getToken()), HttpStatus.OK);
		}).orElseThrow();
	}
}
