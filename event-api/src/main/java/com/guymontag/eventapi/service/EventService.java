package com.guymontag.eventapi.service;

import com.guymontag.eventapi.dto.response.EventDTO;
import com.guymontag.eventapi.dto.response.EventDTOInput;
import com.guymontag.eventapi.util.Page;

public interface EventService {

    EventDTO getEvent(Long eventId);

    Page<EventDTO> getEventPage(int pageNumber, int maxSize);

    EventDTOInput addEvent(EventDTOInput inputEventDTOInput);
}
