package com.myapp.mobile.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRequest {
	
	@NotBlank(message = "이름을 입려해주세요") // notnull은 공백만 체크하고 notblank는 공백과 블랭크를 체크함
	private String name;
	@NotBlank(message = "이메일을 입려해주세요")
	@Email
	private String email;
	
	@NotBlank(message = "패스워드를 입려해주세요")
	@Size(min = 4, max = 10, message = "패스워드의 길이는 4~10")
	private String password;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

}
