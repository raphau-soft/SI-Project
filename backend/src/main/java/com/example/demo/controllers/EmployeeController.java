package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.DeskRepository;
import com.example.demo.dao.EmployeeRepository;
import com.example.demo.dao.PositionRepository;
import com.example.demo.dao.RoomRepository;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.SalaryDTO;
import com.example.demo.entity.Desk;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Position;
import com.example.demo.entity.Room;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private PositionRepository positionRepository;
	
	@Autowired
	private DeskRepository deskRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@PostMapping
	@CrossOrigin(value = "*", maxAge = 3600)
	public void postEmployee(@RequestBody EmployeeDTO employeeDTO) {
		Position position = positionRepository.findById(employeeDTO.getPositionId()).get();
		Desk desk = deskRepository.findById(employeeDTO.getDeskId()).get();
		Room room = roomRepository.findById(employeeDTO.getRoomId()).get();
		Employee employee = new Employee(0, position, room, desk, employeeDTO.getFirstName(), employeeDTO.getLastName(), employeeDTO.getSalary());
		employeeRepository.save(employee);
	}
	
	@GetMapping
	@CrossOrigin(value = "*", maxAge = 3600)
	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}
	
	@GetMapping("/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public Employee getEmployees(@PathVariable int id) {
		return employeeRepository.findById(id).get();
	}
	
	@GetMapping("/minMax")
	@CrossOrigin(value = "*", maxAge = 3600)
	public SalaryDTO getMinMaxSalary() {
		SalaryDTO salary = new SalaryDTO(employeeRepository.min(), employeeRepository.max());
		return salary;
	}
	
	@GetMapping("/room/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public List<Employee> getEmployeesByRoomId(@PathVariable int id) {
		return employeeRepository.findAllByRoomId(id);
	}
	
	@DeleteMapping("/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public void deleteEmployeeById(@PathVariable int id) {
		employeeRepository.deleteById(id);
	}
	
	@PutMapping
	@CrossOrigin(value = "*", maxAge = 3600)
	public void updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
		Desk desk = deskRepository.findById(employeeDTO.getDeskId()).get();
		Room room = roomRepository.findById(employeeDTO.getRoomId()).get();
		Position position = positionRepository.findById(employeeDTO.getPositionId()).get();
		
		Employee employee = new Employee(employeeDTO, room, desk, position);
		employeeRepository.save(employee);
	}
	
}







