package com.example.demo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	@Query(value = "SELECT max(e.salary) FROM employee e WHERE e.company_id = ?1", nativeQuery = true)
	int max(Integer id);
	
	
	@Query(value = "SELECT min(e.salary) FROM employee e WHERE e.company_id = ?1", nativeQuery = true)
	int min(Integer id);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE employee SET position_id = null WHERE position_id = ?1", nativeQuery = true)
	void makePositionNull(Integer id);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE employee SET room_id = null, desk_id = null WHERE room_id = ?1 OR room_id = null", nativeQuery = true)
	void makeRoomNull(Integer id);
	
	List<Employee> findAllByRoomId(int roomId);
	List<Employee> findAllByBuildingId(int buildingId);
	
}
