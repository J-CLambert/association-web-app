package com.guymontag.eventapi.service;

import com.guymontag.eventapi.dto.response.EventDTOInput;
import com.guymontag.eventapi.util.Page;

public interface EventService {

    EventDTOInput getEvent(Long eventId);

    Page<EventDTOInput> getEventPage(int pageNumber, int maxSize);

    EventDTOInput addEvent(EventDTOInput inputEventDTOInput);
}
