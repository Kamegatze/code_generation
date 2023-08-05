package com.kamegatze.code_generation.config.bean_config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class JavaMailSenderBean {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private Integer port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.protocol}")
    private String transportProtocol;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String mailSmtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String  mailSmtpStarttlsEnable;

    @Value("${spring.mail.properties.mail.debug}")
    private String mailDebug;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(this.host);
        javaMailSender.setPort(this.port);

        javaMailSender.setUsername(this.username);
        javaMailSender.setPassword(this.password);

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", this.transportProtocol);
        properties.put("mail.smtp.auth", this.mailSmtpAuth);
        properties.put("mail.smtp.starttls.enable", this.mailSmtpStarttlsEnable);
        properties.put("mail.debug", this.mailDebug);

        return javaMailSender;
    }
}
