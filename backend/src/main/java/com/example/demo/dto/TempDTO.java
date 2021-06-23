package com.example.demo.dto;

import java.util.Arrays;

public class TempDTO {

	RoomDTO room;
	DeskDTO[] desks;
	public RoomDTO getRoom() {
		return room;
	}
	public void setRoom(RoomDTO room) {
		this.room = room;
	}
	public DeskDTO[] getDesks() {
		return desks;
	}
	public void setDesks(DeskDTO[] desks) {
		this.desks = desks;
	}
	public TempDTO(RoomDTO room, DeskDTO[] desks) {
		super();
		this.room = room;
		this.desks = desks;
	}
	public TempDTO() {
		super();
	}
	@Override
	public String toString() {
		return "TempDTO [room=" + room + ", desks=" + Arrays.toString(desks) + "]";
	}
	
}
