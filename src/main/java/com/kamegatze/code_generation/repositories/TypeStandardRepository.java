package com.kamegatze.code_generation.repositories;

import com.kamegatze.code_generation.entities.ETypeStandard;
import com.kamegatze.code_generation.entities.TypeStandard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeStandardRepository extends JpaRepository<TypeStandard, Long> {
    Optional<TypeStandard> findByNameClass(ETypeStandard type);
}
