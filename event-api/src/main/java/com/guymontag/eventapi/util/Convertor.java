package com.guymontag.eventapi.util;

import com.guymontag.eventapi.exception.EventDTOListNullValueException;
import com.guymontag.eventapi.exception.EventListNullValueException;
import com.guymontag.eventapi.exception.EventNullValueException;
import com.guymontag.eventapi.model.dto.EventDTO;
import com.guymontag.eventapi.model.entity.Event;
import com.guymontag.eventapi.util.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Convertor {


    private final Validator validatorImpl;

    @Autowired
    public Convertor(Validator validatorImpl) {
        this.validatorImpl = validatorImpl;
    }


    // EventDTO to Event
    public Event convertDTOToEvent(EventDTO eventDTO) {

        if (validatorImpl.checkFieldsNotNullDTO(eventDTO)) {
            return new Event(
                    eventDTO.getName(),
                    eventDTO.getStartOfEvent(),
                    eventDTO.getDuration(),
                    eventDTO.getDescription(),
                    eventDTO.getStatus(),
                    eventDTO.getLocation()
            );
        } else {
            return null;
        }
    }


    public List<Event> convertDTOsToEvents(List<EventDTO> eventDTOs) {

        if (eventDTOs == null) {
            throw new EventDTOListNullValueException("list is null");
        }

        return eventDTOs.stream().map(eventDTO -> convertDTOToEvent(eventDTO)).toList();
    }


    //Event to EventDTO
    public EventDTO convertEventToDTO(Event event) {
        if (validatorImpl.checkFieldsNotNullEvent(event)) {
            return new EventDTO(
                    event.getName(),
                    event.getStartOfEvent(),
                    event.getDuration(),
                    event.getDescription(),
                    event.getStatus(),
                    event.getLocation()
            );
        } else {
            return null;
        }
    }


    public List<EventDTO> convertEventsToDTOs(List<Event> events) {
        List<EventDTO> eventDTOs = new ArrayList<EventDTO>();
        if (events == null) {
            throw new EventListNullValueException("list is null");
        }

        return events.stream().map(event -> convertEventToDTO(event)).toList();
    }
}
