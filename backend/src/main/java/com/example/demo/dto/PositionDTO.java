package com.example.demo.dto;

import com.example.demo.entity.Position;

public class PositionDTO {

	private int id;
	private String name;
	private double minWage;
	private double maxWage;
	private int usage;
	private int buildingId;
	
	@Override
	public String toString() {
		return "PositionDTO [id=" + id + ", name=" + name + ", minWage=" + minWage + ", maxWage=" + maxWage + ", usage="
				+ usage + "]";
	}
	public PositionDTO(int id, String name, double minWage, double maxWage, int usage, int buildingId) {
		this.id = id;
		this.name = name;
		this.minWage = minWage;
		this.maxWage = maxWage;
		this.usage = usage;
		this.buildingId = buildingId;
	}
	
	public PositionDTO(Position position) {
		this.id = position.getId();
		this.name = position.getName();
		this.minWage = position.getMinWage();
		this.maxWage = position.getMaxWage();
		this.usage = position.getUsage();
		this.buildingId = position.getBuilding().getId();
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
	public int getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}
	
	
	
}
