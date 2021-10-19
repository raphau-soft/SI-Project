package com.example.demo.dto;

import com.example.demo.entity.Company;

public class CompanyResponse {

	private int id;
	
	private String name;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CompanyResponse(int id, String name, int userId) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public CompanyResponse(Company company) {
		super();
		this.id = company.getId();
		this.name = company.getName();
	}
	
	public CompanyResponse() {
		super();
	}
	
}
















