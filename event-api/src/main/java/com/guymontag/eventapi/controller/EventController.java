package com.guymontag.eventapi.controller;

import com.guymontag.eventapi.model.dto.EventDTO;
import com.guymontag.eventapi.model.entity.Event;
import com.guymontag.eventapi.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events/")
public class EventController {

    @Autowired
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{eventId}")
    public EventDTO getEvent(@PathVariable Long eventId) {
        return eventService.getEvent(eventId);
    }
}
