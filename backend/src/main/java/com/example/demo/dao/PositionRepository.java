package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	List<Position> findByCompanyId(int companyId);
	
}
