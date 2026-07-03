package com.guymontag.eventapi.service;

import com.guymontag.eventapi.view.dao.EventDAO;
import com.guymontag.eventapi.exception.*;
import com.guymontag.eventapi.model.dto.EventDTO;
import com.guymontag.eventapi.model.entity.Event;
import com.guymontag.eventapi.util.Convertor;
import com.guymontag.eventapi.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {


    private final EventDAO eventDAO;

    private final Convertor convertor;

    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

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
            throw new IdOutOfBoundException("EventId out of bound");
        }
        Event eventFound = eventDAO.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        log.info("get event: {}", eventFound);

        return convertor.convertEventToDTO(eventFound);
    }

    @Transactional
    @Override
    public Page<EventDTO> getEventPage(int pageNumber, int maxSize) {

        if (pageNumber < 0) {
            throw new NegativePageNumberException("PageNumber is negative");
        }

        if (maxSize > 100) {
            throw new MaxSizeException("MaxSize is bigger than limit 100");
        }

        Long totalEvent = eventDAO.getNumberOfEvent();

        List<EventDTO> eventDTOs = convertor.convertEventsToDTOs(
                eventDAO.getEventPage(pageNumber, maxSize));

        Page<EventDTO> eventDTOPage = new Page<>(eventDTOs, pageNumber, maxSize, totalEvent);

        log.info("get page with : pageNumber: {}, maxSize: {}, totalEvent: {}", pageNumber, maxSize, totalEvent);

        return eventDTOPage;
    }
}
