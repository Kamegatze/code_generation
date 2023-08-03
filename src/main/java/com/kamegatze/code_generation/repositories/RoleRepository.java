package com.kamegatze.code_generation.repositories;

import com.kamegatze.code_generation.entities.ERole;
import com.kamegatze.code_generation.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(ERole role);
}