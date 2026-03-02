package com.college.eventmanagement.config;

import com.college.eventmanagement.model.Role;
import com.college.eventmanagement.model.User;
import com.college.eventmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.admin.email}")
    private String adminEmail;


    @Override
    public void run(String... args) throws Exception {
        boolean userExists = userRepository.existsByRole(Role.ADMIN);
        if(!userExists){
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(Role.ADMIN);
            admin.setName("Admin");
            admin.setBranch("SYSTEM");
            admin.setSem(0);
            admin.setYear(0);
            admin.setCreatedAt(LocalDateTime.now());
            userRepository.save(admin);

        }
    }
}
