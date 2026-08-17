package com.guymontag.eventapi.util.validator;

import com.guymontag.eventapi.dto.response.EventDTO;
import com.guymontag.eventapi.dto.response.EventDTOInput;
import com.guymontag.eventapi.entity.Event;

import java.util.Optional;

public interface Validator {
    boolean checkFieldsNotNullEvent(Event event);

    boolean checkFieldsNotNullDTO(EventDTO eventDTOInput);

    Optional<EventConstraint> newEventTimeCheck(EventDTOInput eventInNow);
}
