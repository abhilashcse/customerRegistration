
 package com.customerRegistration.repository;
 
 import org.springframework.data.jpa.repository.JpaRepository;

import com.customerRegistration.model.User;
 
 
 public interface CustomerProfileRepository extends JpaRepository<User, Integer>{
 
 }
 