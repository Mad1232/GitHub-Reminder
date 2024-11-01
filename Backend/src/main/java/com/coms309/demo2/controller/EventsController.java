package com.coms309.demo2.controller;

import com.coms309.demo2.entity.Events;
import com.coms309.demo2.repository.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventsController {

    @Autowired
    private EventsRepository eventsRepository;

    @PostMapping
    public ResponseEntity<Events> addNewEvent(@RequestBody Events event) {
        event = eventsRepository.save(event);
        return ResponseEntity.ok(event);
    }
}

