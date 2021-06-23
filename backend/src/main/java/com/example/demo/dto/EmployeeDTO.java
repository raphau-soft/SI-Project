package com.example.demo.dto;

public class EmployeeDTO {

	private int id;
	
	private int positionId;
	
	private int roomId;
	
    private int deskId;
	
	private String firstName;
	
	private String lastName;
	
	private int salary;

	public EmployeeDTO(int id, int positionId, int roomId, int deskId, String firstName, String lastName, int salary) {
		super();
		this.id = id;
		this.positionId = positionId;
		this.roomId = roomId;
		this.deskId = deskId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
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


















