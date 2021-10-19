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

import com.example.demo.dao.CompanyRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.CompanyDTO;
import com.example.demo.dto.CompanyResponse;
import com.example.demo.entity.Company;
import com.example.demo.entity.User;
import com.example.demo.security.MyUserDetails;

@RestController
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
		return userRepository.findByLogin(userDetails.getUsername()).get();
	}
	
	@GetMapping()
	@CrossOrigin(value = "*", maxAge = 3600)
	public List<CompanyResponse> getMyCompanies(){
		User user = getUser();
		List<Company> companies = companyRepository.findAllByUserId(user.getId());
		List<CompanyResponse> response = new ArrayList<>();
		Iterator<Company> iterator = companies.iterator();
		while(iterator.hasNext()) {
			response.add(new CompanyResponse(iterator.next()));
		}
		return response;
	}
	
	@GetMapping("/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public CompanyResponse getCompanyById(@PathVariable int id) throws Exception{
		User user = getUser();
		Company company = companyRepository.findById(id).get();
		if(companyRepository.existsByIdAndUserId(id, user.getId()))
			return new CompanyResponse(company);
		else 
			throw new Exception();
	}
	
	@PostMapping()
	@CrossOrigin(value = "*", maxAge = 3600)
	public void postCompany(@RequestBody CompanyDTO companyDTO) {
		User user = getUser();
		Company company = new Company(0, companyDTO.getName(), user);
		companyRepository.save(company);
	}
	
	@DeleteMapping("/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public void deleteCompany(@PathVariable int id) throws Exception {
		User user = getUser();
		if(companyRepository.existsByIdAndUserId(id, user.getId())) {
			companyRepository.deleteById(id);
		} else {
			throw new Exception();
		}
	}
	
	@PutMapping()
	@CrossOrigin(value = "*", maxAge = 3600)
	public void updateCompany(@RequestBody CompanyDTO companyDTO) {
		User user = getUser();
		Company company = companyRepository.findById(companyDTO.getId()).get();
		if(companyRepository.existsByIdAndUserId(company.getId(), user.getId())) {
			User newUser = userRepository.findById(companyDTO.getUserId()).get();
			Company updatedCompany = new Company(companyDTO.getId(), companyDTO.getName(), newUser);
			companyRepository.save(updatedCompany);
		}
		
	}
	
}



















