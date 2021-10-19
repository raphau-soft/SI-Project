package com.example.demo.dto;

import com.example.demo.entity.Employee;

public class EmployeeDTO {

	private int id;
	
	private int positionId;
	
	private int roomId;
	
    private int deskId;
	
	private String firstName;
	
	private String lastName;
	
	private int salary;
	
	private int buildingId;

	public EmployeeDTO(int id, int positionId, int roomId, int deskId, String firstName, String lastName, int salary, int buildingId) {
		super();
		this.id = id;
		this.positionId = positionId;
		this.roomId = roomId;
		this.deskId = deskId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.buildingId = buildingId;
	}
	
	public EmployeeDTO(Employee employee) {
		super();
		this.id = employee.getId();
		if(employee.getPosition() != null)
			this.positionId = employee.getPosition().getId();
		if(employee.getRoom() != null)
			this.roomId = employee.getRoom().getId();
		if(employee.getDesk() != null)
			this.deskId = employee.getDesk().getId();
		this.firstName = employee.getFirstName();
		this.lastName = employee.getLastName();
		this.salary = employee.getSalary();
		this.buildingId = employee.getBuilding().getId();
	}

	public int getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getDeskId() {
		return deskId;
	}

	public void setDeskId(int deskId) {
		this.deskId = deskId;
	}

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public EmployeeDTO() {
		super();
	}

	@Override
	public String toString() {
		return "EmployeeDTO [id=" + id + ", positionId=" + positionId + ", roomId=" + roomId + ", deskId=" + deskId
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", salary=" + salary + "]";
	}

	
	
}


















