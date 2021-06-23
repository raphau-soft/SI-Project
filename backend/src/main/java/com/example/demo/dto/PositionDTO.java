package com.example.demo.dto;

public class PositionDTO {

	private int id;
	private String name;
	private double minWage;
	private double maxWage;
	private int usage;
	
	@Override
	public String toString() {
		return "PositionDTO [id=" + id + ", name=" + name + ", minWage=" + minWage + ", maxWage=" + maxWage + ", usage="
				+ usage + "]";
	}
	public PositionDTO(int id, String name, double minWage, double maxWage, int usage) {
		this.id = id;
		this.name = name;
		this.minWage = minWage;
		this.maxWage = maxWage;
		this.usage = usage;
	}
	public PositionDTO() {
	}
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
	public double getMinWage() {
		return minWage;
	}
	public void setMinWage(double minWage) {
		this.minWage = minWage;
	}
	public double getMaxWage() {
		return maxWage;
	}
	public void setMaxWage(double maxWage) {
		this.maxWage = maxWage;
	}
	public int getUsage() {
		return usage;
	}
	public void setUsage(int usage) {
		this.usage = usage;
	}
	
	
	
}
