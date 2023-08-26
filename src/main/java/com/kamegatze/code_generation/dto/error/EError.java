package com.kamegatze.code_generation.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EError {

    ERROR_EMAIL_EXIST(100),
    ERROR_NICKNAME_EXIST(101),
    ERROR_ROLE_NOT_EXIST(102),
    ERROR_NICKNAME_NOT_FOUND(103),
    ERROR_ENTRY_CODE(104),
    ERROR_PASSWORD_EQUALS_RETRY_PASSWORD(105),
    ERROR_VALIDATION_EXCEPTION(106);

    private final Integer code;

}
