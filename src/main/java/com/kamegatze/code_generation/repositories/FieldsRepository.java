package com.kamegatze.code_generation.repositories;

import com.kamegatze.code_generation.entities.Fields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FieldsRepository extends JpaRepository<Fields, Long> {
}