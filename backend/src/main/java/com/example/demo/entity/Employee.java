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
	
	@ManyToOne(targetEntity = Position.class, fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name="position_id", nullable = true)
	private Position position;
	
	@ManyToOne(targetEntity = Room.class, fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name="room_id", nullable = true)
	private Room room;
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="company_id", nullable = false)
	private Company company;
	
	@OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "desk_id", referencedColumnName = "id", nullable = true)
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



	public Company getCompany() {
		return company;
	}



	public void setCompany(Company company) {
		this.company = company;
	}



	public Employee(int id, Position position, Room room, Desk desk, String firstName, String lastName, int salary, Company company) {
		super();
		this.id = id;
		this.position = position;
		this.room = room;
		this.desk = desk;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.company = company;
	}

	public Employee(EmployeeDTO employeeDTO, Room room, Desk desk, Position position, Company company) {
		super();
		this.id = employeeDTO.getId();
		this.position = position;
		this.room = room;
		this.desk = desk;
		this.firstName = employeeDTO.getFirstName();
		this.lastName = employeeDTO.getLastName();
		this.salary = employeeDTO.getSalary();
		this.company = company;
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








