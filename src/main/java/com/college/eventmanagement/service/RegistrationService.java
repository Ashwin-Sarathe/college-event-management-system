package com.college.eventmanagement.service;

import com.college.eventmanagement.dto.RegistrationRequestDTO;
import com.college.eventmanagement.dto.RegistrationResponseDTO;
import com.college.eventmanagement.model.Event;
import com.college.eventmanagement.model.Registration;
import com.college.eventmanagement.model.RegistrationStatus;
import com.college.eventmanagement.model.User;
import com.college.eventmanagement.repository.EventRepository;
import com.college.eventmanagement.repository.RegistrationRepository;
import com.college.eventmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private RegistrationRepository registrationRepository;

    public RegistrationResponseDTO registerForEvent(RegistrationRequestDTO registrationRequestDTO){

        String userId = registrationRequestDTO.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not found"));
        String eventId = registrationRequestDTO.getEventId();
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event Not found"));

        Optional<Registration> existingRegistration = registrationRepository.findByUserIdAndEventId(userId,eventId);

        //check if user already registered
        if(existingRegistration.isPresent()){
            RegistrationStatus status = existingRegistration.get().getStatus();
            if(status == RegistrationStatus.REGISTERED)
                throw new RuntimeException("User Already Registered for this event!!");
        }

        //capacity check
        long currentCapacity = registrationRepository.countByEventIdAndStatus(eventId, RegistrationStatus.REGISTERED);
        long maxParticipants = event.getMaxParticipants();
        if(currentCapacity >= maxParticipants){
            throw new RuntimeException("Event is full");
        }

        Registration savedRegistration = new Registration();
        savedRegistration.setRegisteredAt(LocalDateTime.now());
        savedRegistration.setUserId(userId);
        savedRegistration.setEventId(eventId);
        savedRegistration.setStatus(RegistrationStatus.REGISTERED);

        Registration saved = registrationRepository.save(savedRegistration);

        return mapToRegResponse(saved);
    }
    private RegistrationResponseDTO mapToRegResponse(Registration savedRegistration){
        RegistrationResponseDTO registrationResponseDTO=new RegistrationResponseDTO();

        registrationResponseDTO.setUserId(savedRegistration.getUserId());
        registrationResponseDTO.setEventId(savedRegistration.getEventId());
        registrationResponseDTO.setId(savedRegistration.getId());
        registrationResponseDTO.setStatus(savedRegistration.getStatus());
        registrationResponseDTO.setRegisteredAt(savedRegistration.getRegisteredAt());

        return registrationResponseDTO;
    }

    public RegistrationResponseDTO cancelRegistration(String regId){
        Registration registration = registrationRepository.findById(regId).orElseThrow(() -> new RuntimeException("Registration not found"));

        if(registration.getStatus() == RegistrationStatus.CANCELLED)
            throw new RuntimeException("Registration already cancelled");

        registration.setStatus(RegistrationStatus.CANCELLED);
        Registration saved = registrationRepository.save(registration);
        return mapToRegResponse(saved);
    }

    public List<RegistrationResponseDTO> findRegistrationByUserId(String userId){
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Registration> registrations = registrationRepository.findByUserId(userId);
        List<RegistrationResponseDTO> registrationResponseDTOS = new ArrayList<>();
        for (Registration r : registrations){
            registrationResponseDTOS.add(mapToRegResponse(r));
        }
        return registrationResponseDTOS;
    }
}
