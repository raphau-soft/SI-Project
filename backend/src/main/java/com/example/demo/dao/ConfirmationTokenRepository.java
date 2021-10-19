package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ConfirmationToken;
import com.example.demo.entity.User;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {

	ConfirmationToken findByConfirmationToken(String confirmationToken);
	ConfirmationToken findByUser(User user);
	
}
