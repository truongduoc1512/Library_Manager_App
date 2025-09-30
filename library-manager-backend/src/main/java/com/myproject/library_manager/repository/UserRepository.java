package com.myproject.library_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.library_manager.model.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);    
}
