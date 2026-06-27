package com.guymontag.eventapi.dao;

import com.guymontag.eventapi.model.entity.Event;

import java.util.Optional;

public interface EventDAO {

    Optional<Event> findById(Long eventId);

    Object getEventPage(int pageNumber);
}
