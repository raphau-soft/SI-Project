package com.example.demo.dto;

import com.example.demo.entity.Building;

public class BuildingResponse {

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

	public BuildingResponse(int id, String name, int userId) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public BuildingResponse(Building company) {
		super();
		this.id = company.getId();
		this.name = company.getName();
	}
	
	public BuildingResponse() {
		super();
	}
	
}
















