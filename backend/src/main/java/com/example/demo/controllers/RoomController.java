package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.DeskRepository;
import com.example.demo.dao.RoomRepository;
import com.example.demo.dto.DeskDTO;
import com.example.demo.dto.TempDTO;
import com.example.demo.entity.Desk;
import com.example.demo.entity.Room;

@RestController
@RequestMapping("/room")
public class RoomController {

	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private DeskRepository deskRepository;
	
	@PostMapping
	@CrossOrigin(value = "*", maxAge = 3600)
	public void postRoom(@RequestBody TempDTO tempDTO) {
		Room room = new Room(0, tempDTO.getRoom().getNumber(), tempDTO.getRoom().getName(), tempDTO.getRoom().isCapacity(), tempDTO.getRoom().getPopulation(), tempDTO.getRoom().getWidth(), tempDTO.getRoom().getHeight());
		roomRepository.save(room);
		DeskDTO[] desks = tempDTO.getDesks();
		for(int i = 0; i < desks.length; i++) {
			Desk desk = new Desk(0, desks[i].getWidth()[0], desks[i].getHeight()[0], desks[i].isTaken(), desks[i].getColor(), desks[i].getRotation(), desks[i].getPositionX(), desks[i].getPositionY(), room);
			deskRepository.save(desk);
		}
	}
	
	@GetMapping()
	@CrossOrigin(value = "*", maxAge = 3600)
	public List<Room> getRooms(){
		return roomRepository.findAll();
	}
	
	@GetMapping("/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public Room getRoom(@PathVariable int id){
		return roomRepository.findById(id).get();
	}
	
	@GetMapping("/increment/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public void incrementRoom(@PathVariable int id){
		Room room = roomRepository.findById(id).get();
		room.setPopulation(room.getPopulation() + 1);
		roomRepository.save(room);
	}
	
	@GetMapping("/decrement/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public void decrementRoom(@PathVariable int id){
		Room room = roomRepository.findById(id).get();
		room.setPopulation(room.getPopulation() - 1);
		roomRepository.save(room);
	}
	
}
