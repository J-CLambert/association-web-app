package com.guymontag.eventapi.util.validator;

import com.guymontag.eventapi.model.dto.EventDTO;
import com.guymontag.eventapi.model.entity.Event;

public interface Validator {
    boolean checkFieldsNotNullEvent(Event event);

    boolean checkFieldsNotNullDTO(EventDTO eventDTO);

    boolean newEventTimeCheck(EventDTO eventInNow);
}
