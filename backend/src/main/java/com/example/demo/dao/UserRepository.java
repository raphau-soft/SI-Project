package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByLogin(String login);
	boolean existsByLoginAndEnabled(String login, boolean enabled);
	boolean existsByLogin(String login);
	boolean existsByEmail(String email);
	User findByEmailIgnoreCase(String email);
}
