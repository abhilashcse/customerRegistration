package com.customerRegistration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customerRegistration.model.User;

@Repository
public interface LoginRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);
}