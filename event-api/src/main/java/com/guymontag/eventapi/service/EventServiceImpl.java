package com.guymontag.eventapi.service;

import com.guymontag.eventapi.dao.EventDAO;
import com.guymontag.eventapi.exception.EventNotFoundException;
import com.guymontag.eventapi.exception.IdOutOfBoundException;
import com.guymontag.eventapi.model.dto.EventDTO;
import com.guymontag.eventapi.model.entity.Event;
import com.guymontag.eventapi.exception.IdValueNullException;
import com.guymontag.eventapi.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {


    private final EventDAO eventDAO;

    private final Convertor convertor;

    @Autowired
    public EventServiceImpl(EventDAO eventDAO, Convertor convertor) {
        this.eventDAO = eventDAO;
        this.convertor = convertor;
    }

    @Override
    public EventDTO getEvent(Long eventId) {

        if (eventId == null) {
            throw new IdValueNullException("EventId has null value");
        }

        if (eventId < 0) {
            throw new IdOutOfBoundException(
                    "EventId out of bound");
        }
        Event evntFound = eventDAO.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));
        return convertor.convertEventToDTO(evntFound);
    }
}
