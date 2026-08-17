package com.guymontag.eventapi.util;

import com.guymontag.eventapi.dto.response.EventDTO;
import com.guymontag.eventapi.dto.response.EventDTOOutPut;
import com.guymontag.eventapi.exception.EventDTOListNullValueException;
import com.guymontag.eventapi.exception.EventListNullValueException;
import com.guymontag.eventapi.dto.response.EventDTOInput;
import com.guymontag.eventapi.entity.Event;
import com.guymontag.eventapi.util.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
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


    public List<Event> convertDTOsToEvents(List<EventDTOInput> eventDTOInputs) {

        if (eventDTOInputs == null) {
            throw new EventDTOListNullValueException("list is null");
        }

        return eventDTOInputs.stream().map(eventDTO -> convertDTOToEvent(eventDTO)).toList();
    }


    //Event to EventDTO
    public EventDTO convertEventToDTO(Event event, DTOType dtoType) {
        if (validatorImpl.checkFieldsNotNullEvent(event)) {
            if (dtoType == DTOType.INPUT) {
                return new EventDTOInput(
                        event.getName(),
                        event.getStartOfEvent(),
                        event.getDuration(),
                        event.getDescription(),
                        event.getStatus(),
                        event.getLocation());
            } else {
                return new EventDTOOutPut(
                        event.getEventId(),
                        event.getName(),
                        event.getStartOfEvent(),
                        event.getDuration(),
                        event.getDescription(),
                        event.getStatus(),
                        event.getLocation());
            }

        }

        if (validatorImpl.checkFieldsNotNullEvent(event)) {
            return new EventDTOInput(
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


    public List<EventDTO> convertEventsToDTOs(List<Event> events, DTOType dtoType) {
        List<EventDTO> eventDTO = new ArrayList<EventDTO>();
        if (events == null) {
            throw new EventListNullValueException("list is null");
        }
        if (dtoType == DTOType.OUTPUT) {
            return events.stream().map(event -> convertEventToDTO(event, DTOType.OUTPUT)).toList();

        } else {
            return events.stream().map(event -> convertEventToDTO(event, DTOType.INPUT)).toList();
        }
    }
}
