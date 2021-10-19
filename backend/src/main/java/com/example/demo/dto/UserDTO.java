package com.example.demo.dto;

public class UserDTO {
	private int id;
	private String login;
	private String email;
	private boolean enabled;	
	private String role;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public UserDTO(int id, String login, String email, boolean enabled, String role) {
		super();
		this.id = id;
		this.login = login;
		this.email = email;
		this.enabled = enabled;
		this.role = role;
	}
	public UserDTO() {
		super();
	}
	
	
}
