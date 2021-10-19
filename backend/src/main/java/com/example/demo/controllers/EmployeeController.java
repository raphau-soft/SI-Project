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
import com.example.demo.dao.DeskRepository;
import com.example.demo.dao.EmployeeRepository;
import com.example.demo.dao.PositionRepository;
import com.example.demo.dao.RoomRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.SalaryDTO;
import com.example.demo.entity.Building;
import com.example.demo.entity.Desk;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Position;
import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import com.example.demo.security.MyUserDetails;

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
	private UserRepository userRepository;
	
	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	private User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
		return userRepository.findByLogin(userDetails.getUsername()).get();
	}
	
	@PostMapping
	@CrossOrigin(value = "*", maxAge = 3600)
	public void postEmployee(@RequestBody EmployeeDTO employeeDTO) throws Exception {
		User user = getUser();
		Building building = buildingRepository.findById(employeeDTO.getBuildingId()).get();
		if(buildingRepository.existsByIdAndUserId(employeeDTO.getBuildingId(), user.getId())) {
			Position position = positionRepository.findById(employeeDTO.getPositionId()).get();
			Desk desk = deskRepository.findById(employeeDTO.getDeskId()).get();
			Room room = roomRepository.findById(employeeDTO.getRoomId()).get();
			Employee employee = new Employee(0, position, room, desk, employeeDTO.getFirstName(), employeeDTO.getLastName(), employeeDTO.getSalary(), building);
			employeeRepository.save(employee);
			position.setUsage(position.getUsage() + 1);
			room.setPopulation(room.getPopulation() + 1);
			positionRepository.save(position);
			roomRepository.save(room);
			desk.setTaken(true);
			desk.setColor("yellow");
			deskRepository.save(desk);
		} else {
			throw new Exception();
		}
	}
	
	@GetMapping("/building/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public List<Employee> getEmployeesByBuildingId(@PathVariable int id) throws Exception {
		User user = getUser();
		if(buildingRepository.existsByIdAndUserId(id, user.getId())) {
			return employeeRepository.findAllByBuildingId(id);
		} else {
			throw new Exception();
		}
	}
	
	@GetMapping("/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public EmployeeDTO getEmployeeById(@PathVariable int id) throws Exception {
		User user = getUser();
		Employee employee = employeeRepository.findById(id).get();
		if(buildingRepository.existsByIdAndUserId(employee.getBuilding().getId(), user.getId())) {
			return new EmployeeDTO(employee);
		} else {
			throw new Exception();
		}
	}
	
	@GetMapping("/minMax/building/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public SalaryDTO getMinMaxSalary(@PathVariable int id) throws Exception {
		User user = getUser();
		if(buildingRepository.existsByIdAndUserId(id, user.getId())) {
			SalaryDTO salary = new SalaryDTO(employeeRepository.min(id), employeeRepository.max(id));
			return salary;
		} else {
			throw new Exception();
		}
	}
	
	@GetMapping("/room/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public List<Employee> getEmployeesByRoomId(@PathVariable int id) throws Exception {
		User user = getUser();
		Room room = roomRepository.findById(id).get();
		if(buildingRepository.existsByIdAndUserId(room.getBuilding().getId(), user.getId())) {
			return employeeRepository.findAllByRoomId(id);
		} else {
			throw new Exception();
		}
	}
	
	@DeleteMapping("/{id}")
	@CrossOrigin(value = "*", maxAge = 3600)
	public void deleteEmployeeById(@PathVariable int id) throws Exception {
		User user = getUser();
		Employee employee = employeeRepository.findById(id).get();
		if(buildingRepository.existsByIdAndUserId(employee.getBuilding().getId(), user.getId())) {
			employeeRepository.deleteById(id);
			Position position = employee.getPosition();
			Room room = employee.getRoom();
			if(position != null) {
				position.setUsage(position.getUsage() - 1);
				positionRepository.save(position);
			}
			if(room != null) {
				room.setPopulation(room.getPopulation() - 1);
				roomRepository.save(room);
			}
		} else {
			throw new Exception();
		}
	}
	
	@PutMapping
	@CrossOrigin(value = "*", maxAge = 3600)
	public void updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
		User user = getUser();
		Desk desk = null;
		Optional<Desk> deskOpt = deskRepository.findById(employeeDTO.getDeskId());
		if(deskOpt.isPresent()) {
			desk = deskOpt.get();
		}
		Room room = null;
		Optional<Room> roomOpt = roomRepository.findById(employeeDTO.getRoomId());
		if(roomOpt.isPresent()) {
			room = roomOpt.get();
		}
		Position position = null;
		Optional<Position> positionOpt = positionRepository.findById(employeeDTO.getPositionId());
		if(positionOpt.isPresent()) {
			position = positionOpt.get();
		}
		Building building = buildingRepository.findById(employeeDTO.getBuildingId()).get();
		Employee myEmployee = employeeRepository.findById(employeeDTO.getId()).get();
		if(buildingRepository.existsByIdAndUserId(building.getId(), user.getId())
				&& buildingRepository.existsByIdAndUserId(myEmployee.getBuilding().getId(), user.getId())) {
			Employee employee = new Employee(employeeDTO, room, desk, position, building);
			employeeRepository.save(employee);
		}
	}
	
}







