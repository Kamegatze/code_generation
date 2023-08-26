package com.kamegatze.code_generation.dto.response;

import lombok.Getter;

@Getter
public enum EResponse {
    
    RESPONSE_CREATED(201),
    RESPONSE_NOT_FOUND(404),
    RESPONSE_SWITCH_PASSWORD(1),
    RESPONSE_CONFIRMED_CODE(2),
    RESPONSE_CHANGE_PASSWORD(3),;

    private final Integer code;
    EResponse(Integer code) {
        this.code = code;
    }

}
