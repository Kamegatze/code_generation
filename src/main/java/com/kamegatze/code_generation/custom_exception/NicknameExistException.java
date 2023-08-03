package com.kamegatze.code_generation.custom_exception;

public class NicknameExistException extends Exception {

    public NicknameExistException(String message) {
        super(message);
    }

}
