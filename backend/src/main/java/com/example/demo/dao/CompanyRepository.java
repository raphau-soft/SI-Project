package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	boolean existsByIdAndUserId(int id, int userId);
	List<Company> findAllByUserId(int userId);
	
}
