package com.college.eventmanagement.repository;

import com.college.eventmanagement.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {

    Optional<Event> findByTitleAndEventDateAndEventTimeAndVenue
            (String title, LocalDate date, LocalTime time, String venue);
}
