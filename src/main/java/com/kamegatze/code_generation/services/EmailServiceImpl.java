package com.kamegatze.code_generation.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {

    private JavaMailSenderImpl javaMailSender;



}
