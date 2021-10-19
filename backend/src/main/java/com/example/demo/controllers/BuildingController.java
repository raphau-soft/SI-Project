package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.BuildingDTO;
import com.example.demo.dto.BuildingResponse;
import com.example.demo.entity.Building;
import com.example.demo.entity.User;
import com.example.demo.security.MyUserDetails;

@RestController
@RequestMapping("/building")
public class BuildingController {

	@Autowired
	private BuildingRepository companyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
		return userRepository.findByLogin(userDetails.getUsername()).get();
	}
	
	@GetMapping()
	@CrossOrigin(value = "*", maxAge = 3600)
	public List<BuildingResponse> getMyBuildings(){
		User user = getUser();
		List<Building> companies = companyRepository.findAllByUserId(user.getId());
		List<BuildingResponse> response = new ArrayList<>();
		Iterator<Building> iterator = companies.iterator();
		while(iterator.hasNext()) {
			response.add(new BuildingResponse(iterator.next()));
		}
		return response;
	}
	
	@GetMapping("/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public BuildingResponse getBuildingById(@PathVariable int id) throws Exception{
		User user = getUser();
		Building company = companyRepository.findById(id).get();
		if(companyRepository.existsByIdAndUserId(id, user.getId()))
			return new BuildingResponse(company);
		else 
			throw new Exception();
	}
	
	@PostMapping()
	@CrossOrigin(value = "*", maxAge = 3600)
	public void postBuilding(@RequestBody BuildingDTO companyDTO) {
		User user = getUser();
		Building company = new Building(0, companyDTO.getName(), user);
		companyRepository.save(company);
	}
	
	@DeleteMapping("/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public void deleteBuilding(@PathVariable int id) throws Exception {
		User user = getUser();
		if(companyRepository.existsByIdAndUserId(id, user.getId())) {
			companyRepository.deleteById(id);
		} else {
			throw new Exception();
		}
	}
	
	@PutMapping()
	@CrossOrigin(value = "*", maxAge = 3600)
	public void updateBuilding(@RequestBody BuildingDTO companyDTO) {
		User user = getUser();
		Building company = companyRepository.findById(companyDTO.getId()).get();
		if(companyRepository.existsByIdAndUserId(company.getId(), user.getId())) {
			User newUser = userRepository.findById(companyDTO.getUserId()).get();
			Building updatedCompany = new Building(companyDTO.getId(), companyDTO.getName(), newUser);
			companyRepository.save(updatedCompany);
		}
		
	}
	
}



















