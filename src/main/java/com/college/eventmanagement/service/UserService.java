package com.college.eventmanagement.service;

import com.college.eventmanagement.dto.RegisterRequestDTO;
import com.college.eventmanagement.dto.UserResponseDTO;
import com.college.eventmanagement.exception.ConflictException;
import com.college.eventmanagement.exception.ResourceNotFoundException;
import com.college.eventmanagement.model.Role;
import com.college.eventmanagement.model.User;
import com.college.eventmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void saveNewUser(RegisterRequestDTO request) {

        //validation
        //created GlobalException class to handle runtime exception
        String username = request.getUsername();
        if(userRepository.existsByUsername(username)){
            throw new ConflictException("Username Already Exists!!");
        }
        String email = request.getEmail();
        if(userRepository.existsByEmail(email)){
            throw new ConflictException("Email Already Exists!!");
        }

        //convert request dto to user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setBranch(request.getBranch());
        user.setSem(request.getSem());
        user.setYear(request.getYear());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());


        userRepository.save(user);

    }

    public UserResponseDTO promoteToAdmin(String userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if(user.getRole()==Role.ADMIN){
            throw new ConflictException("User is already admin");
        }
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setName(user.getName());

        return userResponseDTO;
    }
}
