package com.guymontag.eventapi.util.validator;

import com.guymontag.eventapi.dto.response.EventDTO;
import com.guymontag.eventapi.exception.EventDTONullValueException;
import com.guymontag.eventapi.exception.EventNullValueException;
import com.guymontag.eventapi.dto.response.EventDTOInput;
import com.guymontag.eventapi.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.Optional;

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


    public boolean checkFieldsNotNullDTO(EventDTO eventDTOInput) {

        if (eventDTOInput == null) {
            throw new EventDTONullValueException("EventDTO is null");
        }

        boolean nameIsNotNull = eventDTOInput.getName() != null;
        boolean startOfEventIsNotNull = eventDTOInput.getStartOfEvent() != null;
        boolean descriptionIsNotNull = eventDTOInput.getDescription() != null;
        boolean statusIsNotNull = eventDTOInput.getStatus() != null;
        boolean locationIsNotNull = eventDTOInput.getLocation() != null;

        return nameIsNotNull && startOfEventIsNotNull && descriptionIsNotNull && statusIsNotNull && locationIsNotNull;
    }

    @Override
    public Optional<EventConstraint> newEventTimeCheck(EventDTOInput eventInNow) {
        return constraintsValidEvent.constraints.stream()
                .filter(rejectConditions -> rejectConditions.rule().test(eventInNow)).findFirst();
    }
}
