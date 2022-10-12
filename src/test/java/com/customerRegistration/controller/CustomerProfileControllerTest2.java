package com.customerRegistration.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.customerRegistration.dto.CustomerProfileDTO;
import com.customerRegistration.repository.CustomerProfileRepository;
import com.customerRegistration.service.CustomerProfileServiceImpl;

@SpringBootTest
public class CustomerProfileControllerTest2 {

	@InjectMocks
	CustomerProfileServiceImpl customerProfileServiceImpl = new CustomerProfileServiceImpl();
	
	@Mock
	CustomerProfileRepository customerProfileRepository;
	
	@Test
	@DisplayName("Checking the method with Invalid Response")
	public void testCreateCustomerProfile_EmptyRequestBody() {

		CustomerProfileDTO customerProfileDTO = new CustomerProfileDTO();
		customerProfileDTO.setPassword("Ajita123");
		customerProfileDTO.setAddress("B 203, Elciars Apartment, Floor No., 13, Sector 49, Gurgaon, 121011");
		customerProfileDTO.setEmailAddress("ajitagmail");
		customerProfileDTO.setPan("CHGIR456O");
		customerProfileDTO.setMobileNo("90909090");
		
		
		String actual = customerProfileServiceImpl.createCustomerProfile(customerProfileDTO);
		String expected = "[{\"name\":\"Name\",\"detail\":\"Name is mandatory or missing\"},{\"name\":\"Username\",\"detail\":\"Username is mandatory or missing\"},{\"name\":\"password\",\"detail\":\"Password should contain atleast 6 characters, uppercase, lowercase, special characters and numbers\"},{\"name\":\"address\",\"detail\":\"Address should be less than 50 characters\"},{\"name\":\"state\",\"detail\":\"State field is Mandatory\"},{\"name\":\"country\",\"detail\":\"Country field is Mandatory\"},{\"name\":\"Email\",\"detail\":\"Enter a Vaild Email Address\"},{\"name\":\"Pan\",\"detail\":\"Enter a Vaild PAN Number\"},{\"name\":\"AccountType\",\"detail\":\"Account Type should be Salary or Savings\"},{\"name\":\"MobileNumber\",\"detail\":\"Mobile number should have 10 digits\"},{\"name\":\"DOB\",\"detail\":\"DOB is incorrect or missing\"}]";
		
		assertEquals(actual, expected);
	}
	
	@Test
	@DisplayName("Checking the method with Valid Response")
	public void testCreateCustomerProfile_ValidRequestBody() {

		CustomerProfileDTO customerProfileDTO = new CustomerProfileDTO();
		customerProfileDTO.setName("Ajita");
		customerProfileDTO.setUsername("ajita");
		customerProfileDTO.setPassword("Ajita@123");
		customerProfileDTO.setAddress("123 Gurgaon");
		customerProfileDTO.setState("Haryana");
		customerProfileDTO.setCountry("India");
		customerProfileDTO.setEmailAddress("ajita@gmail.com");
		customerProfileDTO.setPan("CHGIR4560O");
		customerProfileDTO.setMobileNo("9430615330");
		customerProfileDTO.setAccountType("SALARY");
		customerProfileDTO.setDateOfBirth("29-03-1999");
		
		
		String actual = customerProfileServiceImpl.createCustomerProfile(customerProfileDTO);
		String expected = "{\"message\":\"Your registration has been successfully completed\"}";
		
		assertEquals(actual, expected);
	}
	
	@Test
	@DisplayName("Checking the method with Saving Account Type")
	public void testCreateCustomerProfile_SavingAccountType() {

		CustomerProfileDTO customerProfileDTO = new CustomerProfileDTO();
		customerProfileDTO.setName("Ajita");
		customerProfileDTO.setUsername("ajita");
		customerProfileDTO.setPassword("Ajita@123");
		customerProfileDTO.setAddress("123 Gurgaon");
		customerProfileDTO.setState("Haryana");
		customerProfileDTO.setCountry("India");
		customerProfileDTO.setEmailAddress("ajita@gmail.com");
		customerProfileDTO.setPan("CHGIR4560O");
		customerProfileDTO.setMobileNo("9430615330");
		customerProfileDTO.setAccountType("SAVING");
		customerProfileDTO.setDateOfBirth("29-03-1999");
		
		
		String actual = customerProfileServiceImpl.createCustomerProfile(customerProfileDTO);
		String expected = "{\"message\":\"Your registration has been successfully completed\"}";
		
		assertEquals(actual, expected);
	}
	
	
	

}
