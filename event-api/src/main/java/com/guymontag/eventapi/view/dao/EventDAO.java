package com.guymontag.eventapi.dao;

import com.guymontag.eventapi.model.entity.Event;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;
import java.util.Optional;

public interface EventDAO {

    Optional<Event> findById(Long eventId);

    List<Event> getEventPage(int pageNumber, int maxSize);

    Long getNumberOfEvent();
}
