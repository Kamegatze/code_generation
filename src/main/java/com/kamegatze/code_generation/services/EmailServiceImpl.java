package com.kamegatze.code_generation.services;

import com.kamegatze.code_generation.custom_exception.EnterCodeException;
import com.kamegatze.code_generation.dto.auth.SwitchPassword;
import com.kamegatze.code_generation.entities.User;
import com.kamegatze.code_generation.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        simpleMailMessage.setBcc(this.from);

        javaMailSender.send(simpleMailMessage);
    }

    @Transactional
    public String handlerSwitchPassword(SwitchPassword switchPassword) throws UsernameNotFoundException {
        User user = authenticationService.searchUser(switchPassword);

        int onePartCode = (int) (Math.random() * 99) + 100;

        int twoPartCode = (int) (Math.random() * 99) + 100;

        String code = onePartCode + "-" + twoPartCode;

        sendMail(user.getEmail(),
                "Смена пароля",
                "Пожалуйста, введите данный ключ для смена пароля: " + code);

        user.setSwitchPasswordCode(code);

        userRepository.save(user);

        return code;
    }

    @Transactional
    public Boolean handelCheckCode(String code) throws EnterCodeException {
        User user = userRepository.findBySwitchPasswordCode(code)
                .orElseThrow(() -> new EnterCodeException("code enter incorrect"));

        return Boolean.TRUE;
    }
}
