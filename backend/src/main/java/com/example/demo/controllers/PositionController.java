package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.CompanyRepository;
import com.example.demo.dao.EmployeeRepository;
import com.example.demo.dao.PositionRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.PositionDTO;
import com.example.demo.entity.Company;
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
	private CompanyRepository companyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/company/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public List<Position> getPositionsByCompanyId(@PathVariable int id) throws Exception{
		User user = getUser();
		if(companyRepository.existsByIdAndUserId(id, user.getId()))
			return positionRepository.findByCompanyId(id);
		else
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
		if(companyRepository.existsByIdAndUserId(positionDTO.getCompanyId(), user.getId())) {
			Company company = companyRepository.findById(positionDTO.getCompanyId()).get();
			Position position = new Position(0, positionDTO.getName(), positionDTO.getMinWage(), positionDTO.getMaxWage(), positionDTO.getUsage(), company);
			positionRepository.save(position);
		}
	}
	
	@DeleteMapping("/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public void deletePositionById(@PathVariable int id) throws Exception {
		User user = getUser();
		Position position = positionRepository.findById(id).get();
		if(companyRepository.existsByIdAndUserId(position.getCompany().getId(), user.getId())) {
			employeeRepository.makePositionNull(id);
			positionRepository.deleteById(id);
		} else {
			throw new Exception();
		}
	}
	
}

















