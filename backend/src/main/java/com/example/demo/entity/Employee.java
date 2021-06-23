package com.example.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.demo.dto.EmployeeDTO;

@Entity
@Table(name="employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne(targetEntity = Position.class, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="position_id", nullable = false)
	private Position position;
	
	@ManyToOne(targetEntity = Room.class, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="room_id", nullable = false)
	private Room room;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "desk_id", referencedColumnName = "id")
    private Desk desk;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "salary")
	private int salary;
	
	public Employee() {
		super();
	}



	public Employee(int id, Position position, Room room, Desk desk, String firstName, String lastName, int salary) {
		super();
		this.id = id;
		this.position = position;
		this.room = room;
		this.desk = desk;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
	}

	public Employee(EmployeeDTO employeeDTO, Room room, Desk desk, Position position) {
		super();
		this.id = employeeDTO.getId();
		this.position = position;
		this.room = room;
		this.desk = desk;
		this.firstName = employeeDTO.getFirstName();
		this.lastName = employeeDTO.getLastName();
		this.salary = employeeDTO.getSalary();
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

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Desk getDesk() {
		return desk;
	}

	public void setDesk(Desk desk) {
		this.desk = desk;
	}


	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
}








