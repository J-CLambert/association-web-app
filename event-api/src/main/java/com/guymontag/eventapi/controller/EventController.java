package com.guymontag.eventapi.controller;

import com.guymontag.eventapi.model.dto.EventDTO;
import com.guymontag.eventapi.service.EventService;
import com.guymontag.eventapi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{eventId}")
    public EventDTO getEvent(@PathVariable Long eventId) {
        return eventService.getEvent(eventId);
    }

    @GetMapping
    public Page<EventDTO> getEventPage(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "maxSize", required = false, defaultValue = "5") int maxSize
    ) {
        Page<EventDTO> eventDTOPage = eventService.getEventPage(pageNumber, maxSize);
        return eventDTOPage;
    }
}
