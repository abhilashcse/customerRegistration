package com.customerRegistration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.customerRegistration.dto.CustomerProfileDTO;
import com.customerRegistration.dto.ErrorResponse;
import com.customerRegistration.dto.SuccessResponse;
import com.customerRegistration.model.User;
import com.customerRegistration.repository.CustomerProfileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomerProfileServiceImpl {

	@Autowired
	CustomerProfileRepository customerProfileRepository;
	

	public String createCustomerProfile(CustomerProfileDTO customerProfileDTO) {

		String response = null;

		List<ErrorResponse> listErrorResponse = validateCustomerProfile(customerProfileDTO);
		if (!CollectionUtils.isEmpty(listErrorResponse)) {
			response = prepareErrorResponse(listErrorResponse);
			return response;
		}

		User customerProfile = transformIntoCustomerEntity(customerProfileDTO);
		customerProfileRepository.save(customerProfile);

		return prepareSuccessResponse();

	}

	private User transformIntoCustomerEntity(CustomerProfileDTO customerProfileDTO) {

		User customerProfile = new User();
		customerProfile.setName(customerProfileDTO.getName());
		customerProfile.setUsername(customerProfileDTO.getUsername());
		customerProfile.setAccountType(customerProfileDTO.getAccountType());
		customerProfile.setAddress(customerProfileDTO.getAddress());
		customerProfile.setCountry(customerProfileDTO.getCountry());
		customerProfile.setDateOfBirth(customerProfileDTO.getDateOfBirth());
		customerProfile.setEmailAddress(customerProfileDTO.getEmailAddress());
		customerProfile.setPan(customerProfileDTO.getPan());
		customerProfile.setPassword(customerProfileDTO.getPassword());
		customerProfile.setState(customerProfileDTO.getState());
		customerProfile.setMobileNo(customerProfileDTO.getMobileNo());

		return customerProfile;
	}

	private String prepareSuccessResponse() {
		SuccessResponse successResponse = new SuccessResponse();
		successResponse.setMessage("Your registration has been successfully completed");
		ObjectMapper objectMapper = new ObjectMapper();
		String response = null;
		try {
			response = objectMapper.writeValueAsString(successResponse);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	private String prepareErrorResponse(List<ErrorResponse> listErrorResponse) {
		ObjectMapper objectMapper = new ObjectMapper();
		String response = null;
		try {
			response = objectMapper.writeValueAsString(listErrorResponse);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	private List<ErrorResponse> validateCustomerProfile(CustomerProfileDTO customerProfileDTO) {

		List<ErrorResponse> listErrorResponse = new ArrayList<>();

		if (StringUtils.isEmpty(customerProfileDTO.getName())) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("Name");
			errorResponse.setDetail("Name is mandatory or missing");
			listErrorResponse.add(errorResponse);
		}
		if (StringUtils.isEmpty(customerProfileDTO.getUsername())) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("Username");
			errorResponse.setDetail("Username is mandatory or missing");
			listErrorResponse.add(errorResponse);
		}
		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{6,10}$";

		Pattern p = Pattern.compile(regex);

		Matcher m = p.matcher(customerProfileDTO.getPassword());

		if (!m.matches()) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("password");
			errorResponse.setDetail(
					"Password should contain atleast 6 characters, uppercase, lowercase, special characters and numbers");

			listErrorResponse.add(errorResponse);
		}

		if ((customerProfileDTO.getAddress()).length() > 50) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("address");
			errorResponse.setDetail("Address should be less than 50 characters");

			listErrorResponse.add(errorResponse);
		}

		if (StringUtils.isEmpty(customerProfileDTO.getState())) {

			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("state");
			errorResponse.setDetail("State field is Mandatory");

			listErrorResponse.add(errorResponse);
		}

		if (StringUtils.isEmpty(customerProfileDTO.getCountry())) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("country");
			errorResponse.setDetail("Country field is Mandatory");

			listErrorResponse.add(errorResponse);
		}

		String regex1 = "^(.+)@(.+).(.+)$";

		Pattern p1 = Pattern.compile(regex1);

		Matcher m1 = p1.matcher(customerProfileDTO.getEmailAddress());

		if (!m1.matches()) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("Email");
			errorResponse.setDetail("Enter a Vaild Email Address");

			listErrorResponse.add(errorResponse);
		}

		String regex2 = "[A-Z]{5}[0-9]{4}[A-Z]{1}";

		Pattern p2 = Pattern.compile(regex2);

		Matcher m2 = p2.matcher(customerProfileDTO.getPan());

		if (!m2.matches()) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("Pan");
			errorResponse.setDetail("Enter a Vaild PAN Number");

			listErrorResponse.add(errorResponse);

		}

		if (!"SAVING".equals(customerProfileDTO.getAccountType())
				&& !"SALARY".equals(customerProfileDTO.getAccountType())) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("AccountType");
			errorResponse.setDetail("Account Type should be Salary or Savings");

			listErrorResponse.add(errorResponse);
		}

		Pattern p3 = Pattern.compile("[7-9][0-9]{9}");

		Matcher m3 = p3.matcher(customerProfileDTO.getMobileNo());

		if (!m3.matches()) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("MobileNumber");
			errorResponse.setDetail("Mobile number should have 10 digits");

			listErrorResponse.add(errorResponse);
		}

		if (StringUtils.isEmpty(customerProfileDTO.getDateOfBirth())) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("DOB");
			errorResponse.setDetail("DOB is incorrect or missing");

			listErrorResponse.add(errorResponse);
		}
		
		Optional<User> userOpt= customerProfileRepository.findByUsername(customerProfileDTO.getUsername());
		if(userOpt.isPresent()) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setName("UserName");
			errorResponse.setDetail("UserName is already present");
			
			listErrorResponse.add(errorResponse);
		}

		return listErrorResponse;
	}

}
