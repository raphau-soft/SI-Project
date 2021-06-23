package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	@Query(value = "SELECT max(e.salary) FROM employee e", nativeQuery = true)
	int max();
	
	
	@Query(value = "SELECT min(e.salary) FROM employee e", nativeQuery = true)
	int min();
	
	List<Employee> findAllByRoomId(int roomId);
	
}
