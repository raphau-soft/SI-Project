package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {

	boolean existsByIdAndUserId(int id, int userId);
	List<Building> findAllByUserId(int userId);
	
}
