package com.guymontag.eventapi.dao;

import com.guymontag.eventapi.entity.Event;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface EventDAO {

    Optional<Event> findById(Long eventId);

    List<Event> getEventPage(int pageNumber, int maxSize);

    Long getNumberOfEvent();

    boolean eventExistsByBusinessKey(String name, Instant startOfEvent);

    Event addEvent(Event inputEvent);
}
