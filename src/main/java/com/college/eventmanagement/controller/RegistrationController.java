package com.college.eventmanagement.controller;

import com.college.eventmanagement.dto.RegistrationRequestDTO;
import com.college.eventmanagement.dto.RegistrationResponseDTO;
import com.college.eventmanagement.repository.RegistrationRepository;
import com.college.eventmanagement.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{registrationId}/cancel")
    public ResponseEntity<RegistrationResponseDTO> cancelRegistration(@PathVariable String registrationId){

        RegistrationResponseDTO registrationResponseDTO = registrationService.cancelRegistration(registrationId);

        return new ResponseEntity<>(registrationResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RegistrationResponseDTO>> getRegistrationByUserId(@PathVariable String userId){

        List<RegistrationResponseDTO> registrationResponseDTOS = registrationService.findRegistrationByUserId(userId);
        return new ResponseEntity<>(registrationResponseDTOS,HttpStatus.OK);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<RegistrationResponseDTO>> getRegistrationByEventId(@PathVariable String eventId){
        List<RegistrationResponseDTO> registrationResponseDTOS = registrationService.findRegistrationByEventId(eventId);
        return new ResponseEntity<>(registrationResponseDTOS, HttpStatus.OK);
    }
}
