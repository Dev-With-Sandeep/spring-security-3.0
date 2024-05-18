package com.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.dto.AuthRequest;
import com.spring.security.entity.User;
import com.spring.security.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/save")
	public ResponseEntity<User> save(@RequestBody User user) {
		User savedUser = userService.save(user);
		return new ResponseEntity<User>(savedUser, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> generateToken(@RequestBody AuthRequest authRequest) {
		String token = userService.authenticateAndGetToken(authRequest);
		return new ResponseEntity<String>(token, HttpStatus.OK);
	}
}
