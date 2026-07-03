package com.guymontag.eventapi.controller;

import com.guymontag.eventapi.model.dto.EventDTO;
import com.guymontag.eventapi.service.EventService;
import com.guymontag.eventapi.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    private EventService eventService;
    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{eventId}")
    public EventDTO getEvent(@PathVariable Long eventId) {
        log.debug("fetch event with id: {}", eventId);
        return eventService.getEvent(eventId);
    }

    @GetMapping
    public Page<EventDTO> getEventPage(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "maxSize", required = false, defaultValue = "5") int maxSize
    ) {
        log.debug("fetch pagination with request parameters: pageNumber: {}, maxSize: {}", pageNumber, maxSize);
        Page<EventDTO> eventDTOPage = eventService.getEventPage(pageNumber, maxSize);
        return eventDTOPage;
    }
}
