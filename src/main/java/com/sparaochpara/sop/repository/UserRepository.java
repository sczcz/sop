package com.sparaochpara.sop.repository;

import com.sparaochpara.sop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findById(String email);
}
