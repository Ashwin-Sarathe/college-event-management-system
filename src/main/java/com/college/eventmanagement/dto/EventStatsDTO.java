package com.college.eventmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventStatsDTO {
    private String eventId;
    private String title;
    private Integer maxParticipants;
    private Integer currentParticipants;
    private Integer remainingSeats;
}
