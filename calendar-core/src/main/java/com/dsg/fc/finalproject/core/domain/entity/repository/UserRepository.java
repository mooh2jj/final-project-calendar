package com.dsg.fc.finalproject.core.domain.entity.repository;

import com.dsg.fc.finalproject.core.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
