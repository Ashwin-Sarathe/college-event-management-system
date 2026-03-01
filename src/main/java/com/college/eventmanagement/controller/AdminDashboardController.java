package com.college.eventmanagement.controller;


import com.college.eventmanagement.dto.DashboardResponseDTO;
import com.college.eventmanagement.dto.EventStatsDTO;
import com.college.eventmanagement.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<DashboardResponseDTO> getSummary(){
        return new ResponseEntity<>(dashboardService.getSummary(), HttpStatus.OK);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventStatsDTO>> getEventStats(){
        return new ResponseEntity<>(dashboardService.getEventStats(), HttpStatus.OK);
    }
}
