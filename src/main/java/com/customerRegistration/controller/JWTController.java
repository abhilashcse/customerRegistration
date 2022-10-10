package com.customerRegistration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.customerRegistration.model.JWTResponse;
import com.customerRegistration.model.User;
import com.customerRegistration.service.CustomUserDetailsService;
import com.customerRegistration.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin
public class JWTController {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	private UserDetails userDetails;
	
	/*
	 * POST: localhost:8084/login
	 * 
	 * {
	 *     "username" : "admin",
	 *     "password" : "admin"
	 * }
	 */

	@PostMapping("/login")
	public ResponseEntity<?> generateToken(@RequestBody User authenticationRequest) throws Exception {

		System.out.println("Start generateToken");
		System.out.println(authenticationRequest.getUsername() + " " + authenticationRequest.getPassword());
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		} catch (UsernameNotFoundException e) {
			throw new Exception("Incorrect username or password", e);
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		String token = jwtUtil.generateToken(userDetails);

		System.out.println("JWT Token: " + token);
		
		System.out.println("end generateToken");

		return ResponseEntity.ok(new JWTResponse(token));
	}
	
	/*
	 * POST:  localhost:8084/validate
	 * 
	 * {
	 *    "token" : " "
	 * }
	 */

	@PostMapping("/validate")
	public String validateToken(@RequestBody JWTResponse response) {
		System.out.println("start validateToken");
		String token=response.getToken();
		System.out.println(token);
		System.out.println("End validateToken");
		//System.out.println(userDetails.getUsername());
		boolean res= jwtUtil.validateToken(token, userDetails);
		if(res) {
			return userDetails.getUsername()+" "+res;
		}
		else {
			return ""+res;
		}
	}

}