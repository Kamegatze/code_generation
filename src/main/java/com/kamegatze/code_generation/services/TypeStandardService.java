package com.kamegatze.code_generation.services;

import com.kamegatze.code_generation.dto.type_standart.TypeStandardDto;
import com.kamegatze.code_generation.repositories.TypeStandardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TypeStandardService {

    private final TypeStandardRepository typeStandardRepository;

    public List<TypeStandardDto> handleGetTypeStandard() {
        return TypeStandardDto.fromEntityToDto(typeStandardRepository.findAll());
    }
}
