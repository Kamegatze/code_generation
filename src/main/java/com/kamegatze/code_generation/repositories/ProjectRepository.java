package com.kamegatze.code_generation.repositories;


import com.kamegatze.code_generation.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
