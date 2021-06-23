package com.example.demo.dto;

public class DeskDTO {

	private int id;
	
	private double[] width;
	
	private double[] height;
	
	private boolean taken;
	
	private String color;
	
	private double rotation;
	
	private double positionX;
	
	private double positionY;

	public DeskDTO() {
		super();
	}

	public DeskDTO(int id, double[] width, double[] height, boolean taken, String color, double rotation, double positionX,
			double positionY) {
		super();
		this.id = id;
		this.width = width;
		this.height = height;
		this.taken = taken;
		this.color = color;
		this.rotation = rotation;
		this.positionX = positionX;
		this.positionY = positionY;
	}

	@Override
	public String toString() {
		return "DeskDTO [id=" + id + ", width=" + width + ", height=" + height + ", taken=" + taken + ", color=" + color
				+ ", rotation=" + rotation + ", positionX=" + positionX + ", positionY=" + positionY + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double[] getWidth() {
		return width;
	}

	public void setWidth(double[] width) {
		this.width = width;
	}

	public double[] getHeight() {
		return height;
	}

	public void setHeight(double[] height) {
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

	
	
}
