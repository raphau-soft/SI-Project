package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

}
