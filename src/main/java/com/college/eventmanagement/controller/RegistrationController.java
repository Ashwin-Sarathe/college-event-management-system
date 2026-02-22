package com.college.eventmanagement.controller;

import com.college.eventmanagement.dto.RegistrationRequestDTO;
import com.college.eventmanagement.dto.RegistrationResponseDTO;
import com.college.eventmanagement.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<RegistrationResponseDTO> registerForEvent(@Valid @RequestBody RegistrationRequestDTO registrationRequestDTO){
        RegistrationResponseDTO registrationResponseDTO = registrationService.registerForEvent(registrationRequestDTO);

        return new ResponseEntity<>(registrationResponseDTO, HttpStatus.CREATED);
    }
}
