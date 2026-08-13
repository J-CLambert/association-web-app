package com.guymontag.eventapi.util.validator;

import com.guymontag.eventapi.exception.EventDTONullValueException;
import com.guymontag.eventapi.exception.EventNullValueException;
import com.guymontag.eventapi.model.dto.EventDTO;
import com.guymontag.eventapi.model.entity.Event;
import com.guymontag.eventapi.util.EventStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Component
@Lazy
public class ValidatorImpl implements Validator {

    private Clock clock;
    private ConstraintsValidEvent constraintsValidEvent;

    @Autowired
    public ValidatorImpl(Clock clock, ConstraintsValidEvent constraintsValidEvent) {
        this.clock = clock;
        this.constraintsValidEvent = constraintsValidEvent;
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

    @Override
    public boolean newEventTimeCheck(EventDTO eventInNow) {
        return constraintsValidEvent.constraints.stream()
                .anyMatch(rejectConditions -> rejectConditions.rule().test(eventInNow));
    }
}
