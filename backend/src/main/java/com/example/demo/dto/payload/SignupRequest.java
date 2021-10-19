package com.example.demo.dto.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignupRequest {
    @JsonProperty
    private String login;
    @JsonProperty
    private String email;
    @JsonProperty
    private String password;
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
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
	public SignupRequest(String login, String email, String password) {
		super();
		this.login = login;
		this.email = email;
		this.password = password;
	}
    
    

    
}














