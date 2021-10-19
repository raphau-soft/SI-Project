package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.example.demo.dao.CompanyRepository;
import com.example.demo.dao.DeskRepository;
import com.example.demo.dao.EmployeeRepository;
import com.example.demo.dao.RoomRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.DeskDTO;
import com.example.demo.dto.TempDTO;
import com.example.demo.entity.Company;
import com.example.demo.entity.Desk;
import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import com.example.demo.security.MyUserDetails;

@RestController
@RequestMapping("/room")
public class RoomController {

	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private DeskRepository deskRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	private User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
		return userRepository.findByLogin(userDetails.getUsername()).get();
	}
	
	@PostMapping
	@CrossOrigin(value = "*", maxAge = 3600)
	public void postRoom(@RequestBody TempDTO tempDTO) {
		User user = getUser();
		Company company = companyRepository.findById(tempDTO.getRoom().getCompanyId()).get();
		if(companyRepository.existsByIdAndUserId(company.getId(), user.getId())) {
			Room room = new Room(0, tempDTO.getRoom().getNumber(), tempDTO.getRoom().getName(), tempDTO.getRoom().isCapacity(), tempDTO.getRoom().getPopulation(), tempDTO.getRoom().getWidth(), tempDTO.getRoom().getHeight(), company);
			roomRepository.save(room);
			DeskDTO[] desks = tempDTO.getDesks();
			for(int i = 0; i < desks.length; i++) {
				Desk desk = new Desk(0, desks[i].getWidth()[0], desks[i].getHeight()[0], desks[i].isTaken(), desks[i].getColor(), desks[i].getRotation(), desks[i].getPositionX(), desks[i].getPositionY(), room);
				deskRepository.save(desk);
			}
		}
	}
	
	@GetMapping("/company/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public List<Room> getRoomsByCompanyId(@PathVariable int id) throws Exception{
		User user = getUser();
		if(companyRepository.existsByIdAndUserId(id, user.getId()))
			return roomRepository.findAllByCompanyId(id);
		else
			throw new Exception();
	}
	
	@DeleteMapping("/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public void deleteRoomById(@PathVariable int id) throws Exception{
		Room room = roomRepository.findById(id).get();
		User user = getUser();
		if(companyRepository.existsByIdAndUserId(room.getCompany().getId(), user.getId())) {
			employeeRepository.makeRoomNull(id);
			roomRepository.deleteById(id);
		}
		else
			throw new Exception();
	}
	
	@GetMapping("/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public Room getRoom(@PathVariable int id) throws Exception{
		Room room = roomRepository.findById(id).get();
		User user = getUser();
		if(companyRepository.existsByIdAndUserId(room.getCompany().getId(), user.getId()))
			return room;
		else
			throw new Exception();
	}
	
}
