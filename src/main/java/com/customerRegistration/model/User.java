package com.customerRegistration.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name="customer_profile")
public class User {
	@Id 
	 @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	public String name;
	public String username;
	public String password;
	public String address;
	public String state;
	public String country;
	public String emailAddress;
	public String pan;
	public String dateOfBirth;
	public String accountType;
	public String MobileNo;
}