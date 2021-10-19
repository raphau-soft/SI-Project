package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.BuildingRepository;
import com.example.demo.dao.EmployeeRepository;
import com.example.demo.dao.PositionRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.PositionDTO;
import com.example.demo.entity.Building;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Position;
import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import com.example.demo.security.MyUserDetails;

@RestController
@RequestMapping("/position")
public class PositionController {

	@Autowired
	private PositionRepository positionRepository;
	
	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/building/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public List<Position> getPositionsByBuildingId(@PathVariable int id) throws Exception{
		User user = getUser();
		if(buildingRepository.existsByIdAndUserId(id, user.getId()))
			return positionRepository.findByBuildingId(id);
		else
			throw new Exception();
	}
	
	@GetMapping("/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public PositionDTO getPositionsById(@PathVariable int id) throws Exception{
		User user = getUser();
		Optional<Position> position = positionRepository.findById(id);
		if(position.isPresent()){
			Position positionO = position.get();
			Building building = positionO.getBuilding();
			if(buildingRepository.existsByIdAndUserId(building.getId(), user.getId()))
				return new PositionDTO(positionO);
			else
				throw new Exception();
		}
		throw new Exception();
	}
	
	private User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
		return userRepository.findByLogin(userDetails.getUsername()).get();
	}
	
	@PostMapping()
	@CrossOrigin(value = "*", maxAge = 3600)
	public void postPosition(@RequestBody PositionDTO positionDTO){
		User user = getUser();
		if(buildingRepository.existsByIdAndUserId(positionDTO.getBuildingId(), user.getId())) {
			Building building = buildingRepository.findById(positionDTO.getBuildingId()).get();
			Position position = new Position(0, positionDTO.getName(), positionDTO.getMinWage(), positionDTO.getMaxWage(), positionDTO.getUsage(), building);
			positionRepository.save(position);
		}
	}
	
	@PutMapping()
	@CrossOrigin(value = "*", maxAge = 3600)
	public void putPosition(@RequestBody PositionDTO positionDTO){
		User user = getUser();
		if(buildingRepository.existsByIdAndUserId(positionDTO.getBuildingId(), user.getId())) {
			Building building = buildingRepository.findById(positionDTO.getBuildingId()).get();
			Position position = new Position(positionDTO.getId(), positionDTO.getName(), positionDTO.getMinWage(), positionDTO.getMaxWage(), positionDTO.getUsage(), building);
			positionRepository.save(position);
		}
	}
	
	@DeleteMapping("/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public void deletePositionById(@PathVariable int id) throws Exception {
		User user = getUser();
		Position position = positionRepository.findById(id).get();
		if(buildingRepository.existsByIdAndUserId(position.getBuilding().getId(), user.getId())) {
			employeeRepository.makePositionNull(id);
			positionRepository.deleteById(id);
		} else {
			throw new Exception();
		}
	}
	
}

















