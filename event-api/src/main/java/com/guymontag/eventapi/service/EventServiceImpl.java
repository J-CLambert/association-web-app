package com.guymontag.eventapi.service;

import com.guymontag.eventapi.dao.EventDAO;
import com.guymontag.eventapi.exception.EventNotFoundException;
import com.guymontag.eventapi.exception.IdOutOfBoundException;
import com.guymontag.eventapi.entity.Event;
import com.guymontag.eventapi.exception.IdValueNullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {


    @Autowired
    private EventDAO eventDAO;

    public EventServiceImpl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public EventServiceImpl() {

    }

    @Override
    public Event getEvent(Long eventId) {

        if (eventId == null) {
            throw new IdValueNullException("EventId has null value");
        }

        if (eventId < 0) {
            throw new IdOutOfBoundException(
                    "EventId out of bound");
        }

        return eventDAO.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));
    }
}
