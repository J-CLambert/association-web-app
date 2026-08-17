package com.guymontag.eventapi.util;

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
    public Event convertDTOToEvent(EventDTOInput eventDTOInput) {

        if (validatorImpl.checkFieldsNotNullDTO(eventDTOInput)) {
            return new Event(
                    eventDTOInput.getName(),
                    eventDTOInput.getStartOfEvent(),
                    eventDTOInput.getDuration(),
                    eventDTOInput.getDescription(),
                    eventDTOInput.getStatus(),
                    eventDTOInput.getLocation()
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
    public EventDTOInput convertEventToDTO(Event event, DTOType dtoType) {
        if(validatorImpl.checkFieldsNotNullEvent(event)){
            if(dtoType == DTOType.INPUT){

            }else{

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


    public List<EventDTOInput> convertEventsToDTOs(List<Event> events) {
        List<EventDTOInput> eventDTOInputs = new ArrayList<EventDTOInput>();
        if (events == null) {
            throw new EventListNullValueException("list is null");
        }

        return events.stream().map(event -> convertEventToDTO(event)).toList();
    }
}
