package com.guymontag.eventapi;

import com.guymontag.eventapi.exception.EventDTONullValueException;
import com.guymontag.eventapi.exception.EventNullValueException;
import com.guymontag.eventapi.model.dto.EventDTO;
import com.guymontag.eventapi.model.entity.Event;
import com.guymontag.eventapi.util.EventStatus;
import com.guymontag.eventapi.util.validator.Validator;
import com.guymontag.eventapi.util.validator.ValidatorImpl;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidatorImplTest {

    Validator validator = new ValidatorImpl();

    @Test
    void shouldReturnFalseWhenEventFieldIsNull() {
        // Arrange
        Event event = new Event(
                "meting",
                Instant.now(),
                60,
                null,
                "description of event",
                EventStatus.IN_PROGRESS,
                "lausanne");

        boolean excepted = false;

        //Action
        boolean result = validator.checkFieldsNotNullEvent(event);

        //Assert
        assertEquals(excepted, result);
    }

    @Test
    void shouldReturnFalseWhenDTOFieldIsNull() {
        // Arrange
        EventDTO eventDTO = new EventDTO(
                "meting",
                Instant.now(),
                60,
                "description of event",
                EventStatus.IN_PROGRESS,
                null);

        boolean excepted = false;

        //Action
        boolean result = validator.checkFieldsNotNullDTO(eventDTO);

        //Assert
        assertEquals(excepted, result);
    }

    @Test
    void shouldThrowExceptionWhenEventIsNull() {
        //Arrange
        Event nullEvent = null;

        EventNullValueException eventNullValueExceptionExcepted = new EventNullValueException("Event is null");

        //Action
        EventNullValueException eventNullValueExceptionResult =
                assertThrows(EventNullValueException.class, () -> validator.checkFieldsNotNullEvent(nullEvent));

        //Assert
        assertEquals(eventNullValueExceptionExcepted.getMessage(), eventNullValueExceptionResult.getMessage());
        assertEquals(eventNullValueExceptionExcepted.getClass(), eventNullValueExceptionResult.getClass());
    }

    @Test
    void shouldThrowExceptionWhenDTOIsNull() {
        //Arrange
        EventDTO nullEventDTO = null;

        EventDTONullValueException eventDTONullValueExceptionExcepted = new EventDTONullValueException("EventDTO is null");

        //Action
        EventDTONullValueException eventDTONullValueExceptionResult =
                assertThrows(EventDTONullValueException.class, () -> validator.checkFieldsNotNullDTO(nullEventDTO));

        //Assert
        assertEquals(eventDTONullValueExceptionExcepted.getMessage(), eventDTONullValueExceptionResult.getMessage());
        assertEquals(eventDTONullValueExceptionExcepted.getClass(), eventDTONullValueExceptionResult.getClass());
    }
}
