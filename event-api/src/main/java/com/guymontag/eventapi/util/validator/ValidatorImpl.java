package com.guymontag.eventapi.util.validator;

import com.guymontag.eventapi.exception.EventDTONullValueException;
import com.guymontag.eventapi.exception.EventNullValueException;
import com.guymontag.eventapi.model.dto.EventDTO;
import com.guymontag.eventapi.model.entity.Event;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class ValidatorImpl implements Validator {


    public ValidatorImpl() {
    }

    //Validation
    public boolean checkFieldsNotNullEvent(Event event) {

        if (event == null) {
            throw new EventNullValueException("Event is null");
        }

        boolean nameIsNotNull = event.getName() != null;
        boolean startOfEventIsNotNull = event.getStartOfEvent() != null;
        boolean dateOfCreationIsNotNull = event.getDateOfCreation() != null;
        boolean descriptionIsNotNull = event.getDescription() != null;
        boolean statusIsNotNull = event.getStatus() != null;
        boolean locationIsNotNull = event.getLocation() != null;

        return nameIsNotNull && startOfEventIsNotNull && descriptionIsNotNull && statusIsNotNull && locationIsNotNull && dateOfCreationIsNotNull;
    }


    public boolean checkFieldsNotNullDTO(EventDTO eventDTO) {

        if (eventDTO == null) {
            throw new EventDTONullValueException("EventDTO is null");
        }

        boolean nameIsNotNull = eventDTO.getName() != null;
        boolean startOfEventIsNotNull = eventDTO.getStartOfEvent() != null;
        boolean descriptionIsNotNull = eventDTO.getDescription() != null;
        boolean statusIsNotNull = eventDTO.getStatus() != null;
        boolean locationIsNotNull = eventDTO.getLocation() != null;

        return nameIsNotNull && startOfEventIsNotNull && descriptionIsNotNull && statusIsNotNull && locationIsNotNull;
    }
}
