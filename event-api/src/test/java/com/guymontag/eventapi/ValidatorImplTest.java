package com.guymontag.eventapi;

import com.guymontag.eventapi.exception.EventDTONullValueException;
import com.guymontag.eventapi.exception.EventNullValueException;
import com.guymontag.eventapi.model.dto.EventDTO;
import com.guymontag.eventapi.model.entity.Event;
import com.guymontag.eventapi.util.EventStatus;
import com.guymontag.eventapi.util.validator.ValidatorImpl;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorImplTest {


    ValidatorImpl validator = new ValidatorImpl(Clock.fixed(
            Instant.parse("2020-01-01T00:10:00.00Z"),
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

        //Action
        boolean result = validator.checkFieldsNotNullEvent(event);

        //Assert
        assertFalse(result);
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

        //Action
        boolean result = validator.checkFieldsNotNullDTO(eventDTO);

        //Assert
        assertFalse(result);
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
    void shouldThrowExceptionWhenEventIsInThePastAndPlanned() {
        //Arrange
        EventDTO eventInPast = new EventDTO(
                "meting",
                Instant.parse("1999-01-01T00:00:00.00Z"),
                60,
                "description of event",
                EventStatus.PLANNED,
                null);


        PastEventPlannedException pastEventPlannedExceptionExpected = new PastEventPlannedException("Event cannot be planned and be in the past");

        //Action
        PastEventPlannedException pastEventPlannedExceptionResult =
                assertThrows(PastEventPlannedException.class, () -> validator.newEventTimeCheck(eventInPast));

        //Assert
        assertEquals(pastEventPlannedExceptionExpected.getMessage(), pastEventPlannedExceptionResult.getMessage());
    }


    @Test
    void shouldThrowExceptionWhenEventIsInNearPastAndPlanned() {
        //Arrange
        EventDTO eventInPast = new EventDTO(
                "meting",
                Instant.parse("2020-01-01T00:10:00.00Z").minusNanos(1),
                60,
                "description of event",
                EventStatus.PLANNED,
                null);


        PastEventPlannedException pastEventPlannedExceptionExpected = new PastEventPlannedException("Event cannot be planned and be in the past");

        //Action
        PastEventPlannedException pastEventPlannedExceptionResult =
                assertThrows(PastEventPlannedException.class, () -> validator.newEventTimeCheck(eventInPast));

        //Assert
        assertEquals(pastEventPlannedExceptionExpected.getMessage(), pastEventPlannedExceptionResult.getMessage());
    }


    @Test
    void shouldReturnTrueWhenEventIsInThePassAndCompleted() {
        //Arrange
        EventDTO eventInPast = new EventDTO(
                "meting",
                Instant.parse("1999-01-01T00:00:00.00Z"),
                60,
                "description of event",
                EventStatus.COMPLETED,
                null);

        //Action
        boolean newEventIsValidResult = validator.newEventTimeCheck(eventInPast);

        //Assert
        assertTrue(newEventIsValidResult);
    }

    @Test
    void shouldThrowExceptionWhenEventIsInFutureAndCompleted() {
        //Arrange
        EventDTO eventInFuture = new EventDTO(
                "meting",
                Instant.parse("2050-01-01T00:00:00.00Z"),
                60,
                "description of event",
                EventStatus.COMPLETED,
                null);


        FuturEventCompletedException futurEventCompletedExceptionExpected = new FuturEventCompletedException("Event cannot be completed and be in the future");

        //Action
        FuturEventCompletedException futurEventCompletedExceptionResult =
                assertThrows(FuturEventCompletedException.class, () -> validator.newEventTimeCheck(eventInFuture));

        //Assert
        assertEquals(futurEventCompletedExceptionExpected.getMessage(), futurEventCompletedExceptionResult.getMessage());

    }

    @Test
    void shouldReturnTrueWhenEventIsInFuturAndPlanned() {
        //Arrange
        EventDTO eventInFuture = new EventDTO(
                "meting",
                Instant.parse("2050-01-01T00:00:00.00Z"),
                60,
                "description of event",
                EventStatus.PLANNED,
                null);

        //Action
        boolean newEventIsValidResult = validator.newEventTimeCheck(eventInFuture);

        //Assert
        assertTrue(newEventIsValidResult);
    }

    @Test
    void shouldReturnTrueWhenEventIsNowAndIsPlanned() {
        //Arrange
        EventDTO eventInNow = new EventDTO(
                "meting",
                // the date
                Instant.parse("2020-01-01T00:10:00.00Z"),
                60,
                "description of event",
                EventStatus.PLANNED,
                null);


        //Action
        boolean newEventIsValidResult = validator.newEventTimeCheck(eventInNow);

        //Assert
        assertTrue(newEventIsValidResult);
    }

}
