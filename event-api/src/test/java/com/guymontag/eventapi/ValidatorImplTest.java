package com.guymontag.eventapi;

import com.guymontag.eventapi.exception.EventDTONullValueException;
import com.guymontag.eventapi.exception.EventNullValueException;
import com.guymontag.eventapi.model.dto.EventDTO;
import com.guymontag.eventapi.model.entity.Event;
import com.guymontag.eventapi.util.EventStatus;
import com.guymontag.eventapi.util.validator.Validator;
import com.guymontag.eventapi.util.validator.ValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidatorImplTest {


    ValidatorImpl validator = new ValidatorImpl(Clock.fixed(
            Instant.parse("2020-01-01T00:00:00.00Z"),
            ZoneId.of("UTC")));

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

    @Test
    void shouldThrowExceptionWhenEventIsInThePassAndPlanned() {
        //Arrange
        EventDTO evntInPast = new EventDTO(
                "meting",
                Instant.parse("1999-01-01T00:00:00.00Z"),
                60,
                "description of event",
                EventStatus.PLANNED,
                null);


        PastEventPlannedException pastEventPlannedExceptionExpected = new PastEventPlannedException("Event cannot be planned and be in the past");

        //Action
        PastEventPlannedException pastEventPlannedExceptionResult =
                assertThrows(PastEventPlannedException.class, () -> validator.newEventTimeCheck(evntInPast));

        //Assert
        assertEquals(pastEventPlannedExceptionExpected.getMessage(), pastEventPlannedExceptionResult.getMessage());
    }

    @Test
    void shouldReturnTrueWhenEventIsInThePassAndCompleted() {
        //Arrange
        EventDTO evntInPast = new EventDTO(
                "meting",
                Instant.parse("1999-01-01T00:00:00.00Z"),
                60,
                "description of event",
                EventStatus.COMPLETED,
                null);

        //Action
        boolean newEventIsValidResult = validator.newEventTimeCheck(evntInPast);

        //Assert
        assertEquals(true, newEventIsValidResult);
    }

    @Test
    void shouldThrowExceptionWhenEventIsInFutureAndCompleted() {
        //Arrange
        EventDTO evntInPast = new EventDTO(
                "meting",
                Instant.parse("2050-01-01T00:00:00.00Z"),
                60,
                "description of event",
                EventStatus.COMPLETED,
                null);


        FuturEventCompletedException futurEventCompletedExceptionExpected = new FuturEventCompletedException("Event cannot be completed and be in the future");

        //Action
        FuturEventCompletedException futurEventCompletedExceptionResult =
                assertThrows(FuturEventCompletedException.class, () -> validator.newEventTimeCheck(evntInPast));

        //Assert
        assertEquals(futurEventCompletedExceptionExpected.getMessage(), futurEventCompletedExceptionResult.getMessage());

    }

    @Test
    void shouldReturnTrueWhenEventIsInFuturAndPlanned() {
        //Arrange
        EventDTO evntInPast = new EventDTO(
                "meting",
                Instant.parse("2050-01-01T00:00:00.00Z"),
                60,
                "description of event",
                EventStatus.PLANNED,
                null);

        //Action
        boolean newEventIsValidResult = validator.newEventTimeCheck(evntInPast);

        //Assert
        assertEquals(true, newEventIsValidResult);
    }
}
