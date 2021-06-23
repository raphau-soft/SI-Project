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

import com.example.demo.dao.PositionRepository;
import com.example.demo.dto.PositionDTO;
import com.example.demo.entity.Position;
import com.example.demo.entity.Room;

@RestController
@RequestMapping("/position")
public class PositionController {

	@Autowired
	private PositionRepository positionRepository;
	
	@GetMapping()
	@CrossOrigin(value = "*", maxAge = 3600)
	public List<Position> getPositions(){
		return positionRepository.findAll();
	}
	
	@PostMapping()
	@CrossOrigin(value = "*", maxAge = 3600)
	public void postPosition(@RequestBody PositionDTO positionDTO){
		Position position = new Position(0, positionDTO.getName(), positionDTO.getMinWage(), positionDTO.getMaxWage(), positionDTO.getUsage());
		positionRepository.save(position);
	}
	
	@GetMapping("/increment/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public void incrementRoom(@PathVariable int id){
		Position position = positionRepository.findById(id).get();
		position.setUsage(position.getUsage() + 1);
		positionRepository.save(position);
	}
	
	@GetMapping("/decrement/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public void decrementRoom(@PathVariable int id){
		Position position = positionRepository.findById(id).get();
		position.setUsage(position.getUsage() - 1);
		positionRepository.save(position);
	}
	
}
