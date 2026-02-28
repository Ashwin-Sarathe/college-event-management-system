package com.college.eventmanagement.service;

import com.college.eventmanagement.dto.EventRequestDTO;
import com.college.eventmanagement.dto.EventResponseDTO;
import com.college.eventmanagement.exception.ConflictException;
import com.college.eventmanagement.exception.ResourceNotFoundException;
import com.college.eventmanagement.model.Event;
import com.college.eventmanagement.model.Role;
import com.college.eventmanagement.model.User;
import com.college.eventmanagement.repository.EventRepository;
import com.college.eventmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;

    public EventResponseDTO createEvent(EventRequestDTO eventRequest){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User admin = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Optional<Event> optionalEvent= eventRepository.findByTitleAndEventDateAndEventTimeAndVenue
                (
                        eventRequest.getTitle(),
                        eventRequest.getEventDate(),
                        eventRequest.getEventTime(),
                        eventRequest.getVenue()
                );
        if(optionalEvent.isPresent()) throw new ConflictException("Event Already Exists!!");
        else {
            Event newEvent = new Event();
            newEvent.setTitle(eventRequest.getTitle());
            newEvent.setDescription(eventRequest.getDescription());
            newEvent.setEventDate(eventRequest.getEventDate());
            newEvent.setEventTime(eventRequest.getEventTime());
            newEvent.setVenue(eventRequest.getVenue());
            newEvent.setMaxParticipants(eventRequest.getMaxParticipants());
            newEvent.setCreatedBy(admin.getId());
            newEvent.setCurrentParticipants(0);
            newEvent.setCreatedAt(LocalDateTime.now());

            Event savedEvent = eventRepository.save(newEvent);

            return mapToResponse(savedEvent);
        }
    }


    private EventResponseDTO mapToResponse(Event savedEvent) {
        EventResponseDTO eventResponse = new EventResponseDTO();
        eventResponse.setTitle(savedEvent.getTitle());
        eventResponse.setDescription(savedEvent.getDescription());
        eventResponse.setEventDate(savedEvent.getEventDate());
        eventResponse.setEventTime(savedEvent.getEventTime());
        eventResponse.setVenue(savedEvent.getVenue());
        eventResponse.setMaxParticipants(savedEvent.getMaxParticipants());
        eventResponse.setCreatedBy(savedEvent.getCreatedBy());
        eventResponse.setId(savedEvent.getId());
        eventResponse.setCreatedAt(savedEvent.getCreatedAt());
        return eventResponse;
    }

    public List<EventResponseDTO> getAllEvents(){
        List<Event> allEvents = eventRepository.findAll();
        List<EventResponseDTO> allEventDTO = new ArrayList<>();
        for(Event e : allEvents){
            EventResponseDTO eventResponseDTO = mapToResponse(e);
            allEventDTO.add(eventResponseDTO);
        }
        return allEventDTO;
    }

    public EventResponseDTO getEventById(String id){
        Event e = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event Not Found!!"));
        return mapToResponse(e);
    }

    public EventResponseDTO deleteEventById(String id){
        Event e = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event Not Found!"));
        eventRepository.deleteById(id);
        return mapToResponse(e);
    }
}
