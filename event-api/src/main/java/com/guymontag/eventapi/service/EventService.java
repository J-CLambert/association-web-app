package com.guymontag.eventapi.service;

import com.guymontag.eventapi.model.dto.EventDTO;
import com.guymontag.eventapi.model.entity.Event;
import com.guymontag.eventapi.util.Page;

public interface EventService {

    EventDTO getEvent(Long eventId);

    Page<EventDTO> getEventPage(int pageNumber, int maxSize);

    EventDTO addEvent(EventDTO inputEventDTO);
}
