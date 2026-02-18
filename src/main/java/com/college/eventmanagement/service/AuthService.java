package com.college.eventmanagement.service;

import com.college.eventmanagement.dto.LoginRequestDTO;
import com.college.eventmanagement.dto.LoginResponseDTO;
import com.college.eventmanagement.model.User;
import com.college.eventmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){

        //User find by given login credentials
        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));


        boolean pwdMatched = passwordEncoder.matches(
                loginRequestDTO.getPassword(),
                user.getPassword());
        if(!pwdMatched)
            throw new RuntimeException("Invalid Username or Password");

        //user map to LoginResponseDTO
        LoginResponseDTO response = new LoginResponseDTO();
        response.setId(user.getId());
        response.setRole(user.getRole());
        response.setName(user.getName());
        response.setUsername(user.getUsername());

        return response;
    }
}
