package com.coms309.demo2.controller;

import com.coms309.demo2.entity.Events;
import com.coms309.demo2.entity.User;
import com.coms309.demo2.repository.EventsRepository;
import com.coms309.demo2.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Fury Poudel and Madeleine Carydis
 * Creates events
 */
@RestController
@Tag(name = "Events Controller", description = "Manages creation of reminders related to eye drop for pets")

@RequestMapping("/events")
public class EventsController {

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private UserRepository userRepository;
    /**
     * Creates event
     * @param event
     * @return the event that was created
     */
    @Operation(summary = "Creates a new reminder for a user")
    @PostMapping
    public ResponseEntity<Events> addNewEvent(@RequestBody Events event) {
        // Validate the days field
        String days = event.getDays();
        if (days != null && !days.matches("[01]{7}")) {
            return ResponseEntity.badRequest()
                    .body(null); // Invalid format; should be a 7-character string of 0s and 1s
        }
        event = eventsRepository.save(event);
        return ResponseEntity.ok(event);
    }


    /**
     * Retrieves all events for a specific user by user ID.
     *
     * @param userId the ID of the user
     * @return a list of events associated with the user
     */
    @Operation(summary = "Get all reminders for a specific user")
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Events>> getEventsByUserId(@PathVariable Long userId) {
        List<Events> events = eventsRepository.findEventsByUserId(userId);
        if (events.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(events);
    }

    //delete event by petID
    @Operation(summary = "Delete events by Pet ID")
    @DeleteMapping("/pets/{petId}")
    public ResponseEntity<Void> deleteEventsByPetId(@PathVariable int petId) {
        List<Events> events = eventsRepository.findEventsByPetId(petId);
        if (events.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        eventsRepository.deleteEventsByPetId(petId);
        return ResponseEntity.noContent().build();
    }


}

