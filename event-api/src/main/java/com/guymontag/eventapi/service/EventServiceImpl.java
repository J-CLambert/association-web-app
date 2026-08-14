package com.guymontag.eventapi.service;

import com.guymontag.eventapi.dao.EventDAO;
import com.guymontag.eventapi.exception.*;
import com.guymontag.eventapi.dto.EventDTOInput;
import com.guymontag.eventapi.entity.Event;
import com.guymontag.eventapi.util.Convertor;
import com.guymontag.eventapi.util.Page;
import com.guymontag.eventapi.util.validator.EventConstraint;
import com.guymontag.eventapi.util.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {


    private final EventDAO eventDAO;

    private final Convertor convertor;

    private final Validator validator;

    @Autowired
    public EventServiceImpl(EventDAO eventDAO, Convertor convertor, Validator validator) {
        this.eventDAO = eventDAO;
        this.convertor = convertor;
        this.validator = validator;
    }

    @Override
    public EventDTOInput getEvent(Long eventId) {

        if (eventId == null) {
            throw new IdValueNullException("EventId has null value");
        }

        if (eventId < 0) {
            throw new IdOutOfBoundException("EventId out of bound");
        }
        Event evntFound = eventDAO.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));
        return convertor.convertEventToDTO(evntFound);
    }

    @Transactional
    @Override
    public Page<EventDTOInput> getEventPage(int pageNumber, int maxSize) {

        if (pageNumber < 0) {
            throw new NegativePageNumberException("PageNumber is negative");
        }

        if (maxSize > 100) {
            throw new MaxSizeException("MaxSize is bigger than limit 100");
        }

        Long totalEvent = eventDAO.getNumberOfEvent();

        List<EventDTOInput> eventDTOInputs = convertor.convertEventsToDTOs(
                eventDAO.getEventPage(pageNumber, maxSize));

        Page<EventDTOInput> eventDTOPage = new Page<>(eventDTOInputs, pageNumber, maxSize, totalEvent);

        return eventDTOPage;
    }

    @Transactional
    @Override
    public EventDTOInput addEvent(EventDTOInput inputEventDTOInput) {
        Optional<EventConstraint> conditionOfRejetPassed = validator.newEventTimeCheck(inputEventDTOInput);

        if (conditionOfRejetPassed.isPresent()) {
            throw conditionOfRejetPassed.get().exception().get();
        }

        if (eventDAO.eventExistsByBusinessKey(inputEventDTOInput.getName(), inputEventDTOInput.getStartOfEvent())) {
            throw new AlreadyExistEventException("Event already exist");
        }

        Event inputEvent = convertor.convertDTOToEvent(inputEventDTOInput);

        eventDAO.addEvent(inputEvent);

        return inputEventDTOInput;
    }
}
