package com.customerRegistration.service;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.customerRegistration.repository.LoginRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private LoginRepository loginRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("Start loadUserByUsername");

		com.customerRegistration.model.User user = loginRepository.findByUsername(username);

		if (user == null) {

			System.out.println("User not found:" + username);
			throw new UsernameNotFoundException("User not found !!");
		}
		System.out.println("User found: " + user.getUsername());
		
		System.out.println("end loadUserByUsername");

		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

}