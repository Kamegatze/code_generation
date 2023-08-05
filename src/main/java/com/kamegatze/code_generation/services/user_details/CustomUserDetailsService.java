package com.kamegatze.code_generation.services.user_details;

import com.kamegatze.code_generation.jwt.JwtUtils;
import com.kamegatze.code_generation.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kamegatze.code_generation.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrNickname(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with nickname: " + username));

        return new CustomUserDetails(user);
    }
}