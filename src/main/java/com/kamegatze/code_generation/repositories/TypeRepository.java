package com.kamegatze.code_generation.repositories;

import com.kamegatze.code_generation.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Long> {
    Boolean existsByFullName(String fullName);

    Optional<Type> findByFullName(String fullName);
}
