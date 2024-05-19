package com.spring.security.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.entity.User;
import com.spring.security.service.CustomizedUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/greetings")
public class GreetingsController {

	@Autowired
	private CustomizedUserDetailsService userDetailsService;

	@GetMapping("/hello")
	public ResponseEntity<String> sayHello() {
		return new ResponseEntity<String>("Hello from API", HttpStatus.OK);
	}

	@GetMapping("/bye")
	public ResponseEntity<String> sayBye() {
		return new ResponseEntity<String>("Bye from API", HttpStatus.OK);
	}

	@GetMapping("/admin")
//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> forAdmin(HttpServletRequest request) {
		Optional<User> user = userDetailsService.getUserDetails();
		if (user.isPresent() && user.get().getRole().equals("ROLE_ADMIN")) {
			return new ResponseEntity<String>("Hello Admin!", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("UnAuthorized", HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/user")
//	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<String> forUser() {
		Optional<User> user = userDetailsService.getUserDetails();
		if (user.isPresent() && user.get().getRole().equals("ROLE_USER")) {
			return new ResponseEntity<String>("Hello User!", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("UnAuthorized", HttpStatus.UNAUTHORIZED);
		}
	}

}
