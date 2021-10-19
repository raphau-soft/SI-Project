package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserRepository;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.security.MyUserDetails;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	private User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
		return userRepository.findByLogin(userDetails.getUsername()).get();
	}
	
	@GetMapping()
	public User getUserData() {
		return getUser();
	}
	
	@GetMapping("/all")
	public List<User> getUsers(){
		return userRepository.findAll();
	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}
	
	@PutMapping()
	public void putUser(@RequestBody UserDTO userDTO) {	
		User user = getUser();
		if(user.getId() == userDTO.getId()) {
			user.setEmail(userDTO.getEmail());
		}	
	}
	
}
















