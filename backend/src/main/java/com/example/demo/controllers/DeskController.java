package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.DeskRepository;
import com.example.demo.dao.RoomRepository;
import com.example.demo.dto.DeskDTO;
import com.example.demo.entity.Desk;

@RestController
@RequestMapping("/desk")
public class DeskController {

	@Autowired
	private DeskRepository deskRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@GetMapping()
	@CrossOrigin(value = "*", maxAge = 3600)
	public List<Desk> getDesks(){
		return deskRepository.findAll();
	}
	
	@GetMapping("/room/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public List<Desk> getDesksByRoomId(@PathVariable int id){
		return deskRepository.findAllByRoomId(id);
	}
	
	@GetMapping("/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public Desk getDeskById(@PathVariable int id){
		return deskRepository.findById(id).get();
	}
	
	@PutMapping()
	@CrossOrigin(value = "*", maxAge = 3600)
	public void putDesk(@RequestBody DeskDTO deskDTO){
		Desk desk = deskRepository.findById(deskDTO.getId()).get();
		deskRepository.save(new Desk(deskDTO, desk.getRoom()));
	}
	
	@PutMapping("/desks")
	@CrossOrigin(value = "*", maxAge = 3600)
	public void putDesks(@RequestBody DeskDTO[] desksDTO){
		for(int i = 0; i < desksDTO.length; i++) {
			Desk desk = deskRepository.findById(desksDTO[i].getId()).get();
			deskRepository.save(new Desk(desksDTO[i], desk.getRoom()));
		}
	}
	
	@PutMapping("/Ddesks")
	@CrossOrigin(value = "*", maxAge = 3600)
	public void deleteDesks(@RequestBody DeskDTO[] desksDTO){
		for(int i = 0; i < desksDTO.length; i++) {
			deskRepository.deleteById(desksDTO[i].getId());
		}
	}
	
}















