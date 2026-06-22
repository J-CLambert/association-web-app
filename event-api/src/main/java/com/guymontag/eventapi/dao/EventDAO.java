package com.guymontag.eventapi.dao;

import com.guymontag.eventapi.model.Event;

import java.util.Optional;

public interface EventDAO {

    Optional<Event> findById(Long eventId);
}
