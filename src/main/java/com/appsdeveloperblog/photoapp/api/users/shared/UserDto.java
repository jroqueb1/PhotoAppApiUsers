package com.appsdeveloperblog.photoapp.api.users.shared;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.appsdeveloperblog.photoapp.api.users.model.AlbumResponseModel;

public class UserDto implements Serializable {
	private static final long serialVersionUID = -4303372587659097487L;

	public UserDto() {
		this.userId = UUID.randomUUID().toString();
	}

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String encryptedPassword;
	private String userId;

	private List<AlbumResponseModel> albums;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<AlbumResponseModel> getAlbums() {
		return albums;
	}

	public void setAlbums(List<AlbumResponseModel> albums) {
		this.albums = albums;
	}

	@Override
	public String toString() {
		return "UserDto [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password="
				+ password + ", encryptedPassword=" + encryptedPassword + ", userId=" + userId + "]";
	}

}
