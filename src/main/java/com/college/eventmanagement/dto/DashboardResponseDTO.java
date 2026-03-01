package com.college.eventmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDTO {

    private Long totalUsers;
    private Long totalEvents;
    private Long totalRegistrations;
    private Long totalActiveRegistrations;

}
