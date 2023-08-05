package com.kamegatze.code_generation.services;

import com.kamegatze.code_generation.dto.auth.SwitchPassword;
import com.kamegatze.code_generation.entities.User;
import com.kamegatze.code_generation.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailServiceImpl {

    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;

    private final JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    private void sendMail(String to, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(this.from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);
    }

    @Transactional
    public void  handlerSwitchPassword(SwitchPassword switchPassword) {
        User user = authenticationService.searchUser(switchPassword);

        int code = (int) (Math.random()*100000) + 100000;

        sendMail(user.getEmail(),
                "Смена пароля",
                "Пожалуйста, введите данный ключ для смена пароля:\n " + code);

        user.setSwitch_password_code(String.valueOf(code));

        userRepository.save(user);
    }
}
