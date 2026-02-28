package com.college.eventmanagement.dto;

import com.college.eventmanagement.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private String id;

    private String username;

    private String name;

    private Role role;

    private String token;
}
