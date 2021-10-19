package com.example.demo.dto;

import java.util.Arrays;

public class RoomDTO {

	private int id;
	
	private int number;
	
	private String name;
	
	private int capacity;
	
	private int population;
	
	private double width;
	
	private double height;
	
	private int[] deskID;
	
	private int companyId;

	public RoomDTO() {
		super();
	}

	public RoomDTO(int id, int number, String name, int capacity, int population, double width,
			double height, int[] deskID, int companyId) {
		super();
		this.id = id;
		this.number = number;
		this.name = name;
		this.capacity = capacity;
		this.population = population;
		this.width = width;
		this.height = height;
		this.deskID = deskID;
		this.companyId = companyId;
	}



	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "RoomDTO [id=" + id + ", number=" + number + ", name=" + name + ", capacity=" + capacity
				+ ", population=" + population + ", width=" + width + ", height=" + height + ", deskID="
				+ Arrays.toString(deskID) + "]";
	}

	public int[] getDeskID() {
		return deskID;
	}

	public void setDeskID(int[] deskID) {
		this.deskID = deskID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int isCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	
}
