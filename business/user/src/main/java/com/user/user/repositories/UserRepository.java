package com.user.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.user.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
}
