package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.CompanyRepository;
import com.example.demo.dao.DeskRepository;
import com.example.demo.dao.RoomRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.DeskDTO;
import com.example.demo.entity.Company;
import com.example.demo.entity.Desk;
import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import com.example.demo.security.MyUserDetails;

@RestController
@RequestMapping("/desk")
public class DeskController {

	@Autowired
	private DeskRepository deskRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
		return userRepository.findByLogin(userDetails.getUsername()).get();
	}
	
	@GetMapping("/company/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public List<Desk> getDesksByCompanyId(@PathVariable int id) throws Exception{
		User user = getUser();
		List<Room> rooms = roomRepository.findAllByCompanyId(id);
		List<Desk> desks = new ArrayList<>();
		if(companyRepository.existsByIdAndUserId(id, user.getId())){
			for(int i = 0; i < rooms.size(); i++) {
				desks = Stream.concat(desks.stream(), deskRepository.findAllByRoomId(rooms.get(i).getId()).stream()).collect(Collectors.toList());
			}
			return desks;
		} else {
			throw new Exception();
		}
	}
		
	
	@GetMapping("/room/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public List<Desk> getDesksByRoomId(@PathVariable int id) throws Exception{
		User user = getUser();
		Company company = roomRepository.findById(id).get().getCompany();
		
		if(companyRepository.existsByIdAndUserId(company.getId(), user.getId())){
			return deskRepository.findAllByRoomId(id);
		} else {
			throw new Exception();
		}
	}
	
	@GetMapping("/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public Desk getDeskById(@PathVariable int id) throws Exception{
		User user = getUser();
		Desk desk = deskRepository.findById(id).get();
		
		if(companyRepository.existsByIdAndUserId(desk.getRoom().getCompany().getId(), user.getId())){
			return desk;
		} else {
			throw new Exception();
		}
	}
	
	@PutMapping()
	@CrossOrigin(value = "*", maxAge = 3600)
	public void putDesk(@RequestBody DeskDTO deskDTO) throws Exception{
		User user = getUser();
		Desk desk = deskRepository.findById(deskDTO.getId()).get();
		if(companyRepository.existsByIdAndUserId(desk.getRoom().getCompany().getId(), user.getId())){
			deskRepository.save(new Desk(deskDTO, desk.getRoom()));
		} else {
			throw new Exception();
		}
	}
	
	@PutMapping("/desks")
	@CrossOrigin(value = "*", maxAge = 3600)
	public void putDesks(@RequestBody DeskDTO[] desksDTO) throws Exception{
		User user = getUser();
		for(int i = 0; i < desksDTO.length; i++) {
			Desk desk = deskRepository.findById(desksDTO[i].getId()).get();
			if(companyRepository.existsByIdAndUserId(desk.getRoom().getCompany().getId(), user.getId())){
				deskRepository.save(new Desk(desksDTO[i], desk.getRoom()));
			} else {
				throw new Exception();
			}
		}
	}
	
	@PutMapping("/Ddesks")
	@CrossOrigin(value = "*", maxAge = 3600)
	public void deleteDesks(@RequestBody DeskDTO[] desksDTO) throws Exception{
		User user = getUser();
		for(int i = 0; i < desksDTO.length; i++) {
			Desk desk = deskRepository.findById(desksDTO[i].getId()).get();
			if(companyRepository.existsByIdAndUserId(desk.getRoom().getCompany().getId(), user.getId())){
				deskRepository.deleteById(desksDTO[i].getId());
			} else {
				throw new Exception();
			}
		}
	}
	
}















