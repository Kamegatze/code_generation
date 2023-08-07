package com.kamegatze.code_generation.repositories;

import com.kamegatze.code_generation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmailOrNickname(String email, String nickname);

    Boolean existsByEmail(String email);

    Boolean existsByNickname(String nickname);

    Optional<User> findBySwitchPasswordCode(String code);
}