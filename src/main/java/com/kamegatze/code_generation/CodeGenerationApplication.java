package com.kamegatze.code_generation;


import com.kamegatze.code_generation.logic_generate.GenerateClass;
import com.kamegatze.code_generation.logic_generate.GenerateEntity;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.javapoet.ClassName;
import org.springframework.javapoet.FieldSpec;

@SpringBootApplication
public class CodeGenerationApplication implements CommandLineRunner {

	@Value("${application.unzip.file.path}")
	private String path;

	@Override
	public void run(String... args) throws Exception {
	}

	public static void main(String[] args) {
		SpringApplication.run(CodeGenerationApplication.class, args);
	}
}