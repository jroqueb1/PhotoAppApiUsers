package com.appsdeveloperblog.photoapp.api.users.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {
	@NotNull(message="First name cannot be null.")
	@Size(min= 2, max=255, message = "Character lenght must be 2-255.")
	private String firstName;
	
	@NotNull(message="Last name cannot be null.")
	@Size(min= 2, max=255, message = "Character lenght must between 2-255.")
	private String lastName;
	
	@NotNull(message="Password cannot be null.")
	@Size(min= 8, max=16, message="Password lenght must between 8-16.")
	private String password;
	
	@Email(message="Invalid email provided.")
	private String email;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "CreateUserRequestModel [firstName=" + firstName + ", lastName=" + 
				lastName + ", password=" + password + ", email=" + email + "]";
	}

	
}
