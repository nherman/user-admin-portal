package com.nherman.useradminportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nherman.useradminportal.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
