package com.college.eventmanagement.repository;

import com.college.eventmanagement.model.Registration;
import com.college.eventmanagement.model.RegistrationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends MongoRepository<Registration, String> {

    Optional<Registration> findByUserIdAndEventId(String userId, String eventId);

    long countByEventIdAndStatus(String eventId, RegistrationStatus status);

    List<Registration> findByUserId(String userId);
}
