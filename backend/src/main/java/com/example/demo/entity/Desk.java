package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.dto.DeskDTO;

@Entity
@Table(name="desk")
public class Desk {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "width")
	private double width;
	
	@Column(name = "height")
	private double height;
	
	@Column(name = "taken")
	private boolean taken;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "rotation")
	private double rotation;
	
	@Column(name = "positionX")
	private double positionX;
	
	@Column(name = "positionY")
	private double positionY;
	
	@ManyToOne(targetEntity = Room.class, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="room_id", nullable = false)
	private Room room;

	public Desk() {
		super();
	}

	public Desk(int id, double width, double height, boolean taken, String color, double rotation, double positionX,
			double positionY, Room room) {
		super();
		this.id = id;
		this.width = width;
		this.height = height;
		this.taken = taken;
		this.color = color;
		this.rotation = rotation;
		this.positionX = positionX;
		this.positionY = positionY;
		this.room = room;
	}
	
	public Desk(DeskDTO deskDTO, Room room) {
		super();
		this.id = deskDTO.getId();
		this.width = deskDTO.getWidth()[0];
		this.height = deskDTO.getHeight()[0];
		this.taken = deskDTO.isTaken();
		this.color = deskDTO.getColor();
		this.rotation = deskDTO.getRotation();
		this.positionX = deskDTO.getPositionX();
		this.positionY = deskDTO.getPositionY();
		this.room = room;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public boolean isTaken() {
		return taken;
	}

	public void setTaken(boolean taken) {
		this.taken = taken;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
}
