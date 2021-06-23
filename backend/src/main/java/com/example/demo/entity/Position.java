package com.example.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="position")
public class Position {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "min_wage")
	private double minWage;
	
	@Column(name = "max_wage")
	private double maxWage;
	
	@Column(name = "_usage")
	private int usage;
	
	@OneToMany(mappedBy = "position", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonBackReference
	private List<Employee> employees;

	public Position() {
	}

	@Override
	public String toString() {
		return "Position [id=" + id + ", name=" + name + ", minWage=" + minWage + ", maxWage=" + maxWage + ", usage="
				+ usage +  "]";
	}

	public Position(int id, String name, double minWage, double maxWage, int usage) {
		this.id = id;
		this.name = name;
		this.minWage = minWage;
		this.maxWage = maxWage;
		this.usage = usage;
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
