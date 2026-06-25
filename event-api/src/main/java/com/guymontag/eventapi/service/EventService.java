package com.guymontag.eventapi.service;

import com.guymontag.eventapi.model.dto.EventDTO;
import com.guymontag.eventapi.model.entity.Event;

public interface EventService {

    EventDTO getEvent(Long eventId);
}
