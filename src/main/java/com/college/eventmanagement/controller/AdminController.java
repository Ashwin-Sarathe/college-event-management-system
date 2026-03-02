package com.college.eventmanagement.controller;

import com.college.eventmanagement.dto.UserResponseDTO;
import com.college.eventmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @PutMapping("/users/{id}/promote")
    public ResponseEntity<UserResponseDTO> promoteToAdmin(@PathVariable String id){
        return new ResponseEntity<>(userService.promoteToAdmin(id), HttpStatus.OK);
    }

}
