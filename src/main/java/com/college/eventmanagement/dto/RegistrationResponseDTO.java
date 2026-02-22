package com.college.eventmanagement.dto;

import com.college.eventmanagement.model.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseDTO {

    private String id;
    private String userId;
    private String eventId;
    private LocalDateTime registeredAt;
    private RegistrationStatus status;
}
