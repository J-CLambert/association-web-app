package com.guymontag.eventapi;

import com.guymontag.eventapi.exception.EventDTONullValueException;
import com.guymontag.eventapi.exception.EventNullValueException;
import com.guymontag.eventapi.exception.FuturEventCompletedException;
import com.guymontag.eventapi.exception.PastEventPlannedException;
import com.guymontag.eventapi.dto.EventDTOInput;
import com.guymontag.eventapi.entity.Event;
import com.guymontag.eventapi.util.EventStatus;
import com.guymontag.eventapi.util.validator.ConstraintsValidEvent;
import com.guymontag.eventapi.util.validator.EventConstraint;
import com.guymontag.eventapi.util.validator.ValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorImplTest {

    Clock fixedClock = Clock.fixed(Instant.parse("2020-01-01T00:10:00.00Z"), ZoneId.of("UTC"));
    ConstraintsValidEvent constraintsValidEvent = new ConstraintsValidEvent(fixedClock);
    ValidatorImpl validator = new ValidatorImpl(fixedClock, constraintsValidEvent);

    @Test
    void shouldReturnFalseWhenEventFieldIsNull() {
        // Arrange
        Event event = new Event(
                "meting",
                Instant.now(fixedClock),
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

    static Stream<EventDTOInput> eventDTOWithNullField() {

        EventDTOInput exempleOfEventDTOInput = new EventDTOInput(
                "meeting",
                Instant.parse("2020-01-01T00:10:00.00Z"),
                60,
                "description of event",
                EventStatus.IN_PROGRESS,
                "Lausanne");

        EventDTOInput eventDTONullStartOfEventInput = exempleOfEventDTOInput.copy();
        eventDTONullStartOfEventInput.setStartOfEvent(null);

        EventDTOInput eventDTOInputNullDescription = exempleOfEventDTOInput.copy();
        eventDTOInputNullDescription.setDescription(null);

        EventDTOInput eventDTOInputNullStatus = exempleOfEventDTOInput.copy();
        eventDTOInputNullStatus.setStatus(null);

        EventDTOInput eventDTOInputNullLocation = exempleOfEventDTOInput.copy();
        eventDTOInputNullLocation.setLocation(null);

        EventDTOInput eventDTOInputNullName = exempleOfEventDTOInput.copy();
        eventDTOInputNullName.setName(null);

        return Stream.of(eventDTOInputNullName, eventDTOInputNullDescription, eventDTOInputNullLocation, eventDTOInputNullStatus, eventDTONullStartOfEventInput);
    }

    @ParameterizedTest
    @MethodSource("eventDTOWithNullField")
    void shouldReturnFalseWhenDTOFieldIsNull(EventDTOInput eventDTOInput) {
        // Arrange

        //Action
        boolean result = validator.checkFieldsNotNullDTO(eventDTOInput);

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
        EventDTOInput nullEventDTOInput = null;

        EventDTONullValueException eventDTONullValueExceptionExcepted = new EventDTONullValueException("EventDTO is null");

        //Action
        EventDTONullValueException eventDTONullValueExceptionResult =
                assertThrows(EventDTONullValueException.class, () -> validator.checkFieldsNotNullDTO(nullEventDTOInput));

        //Assert
        assertEquals(eventDTONullValueExceptionExcepted.getMessage(), eventDTONullValueExceptionResult.getMessage());
        assertEquals(eventDTONullValueExceptionExcepted.getClass(), eventDTONullValueExceptionResult.getClass());
    }

    @Test
    void shouldThrowExceptionWhenEventIsInThePastAndPlanned() {
        //Arrange
        EventDTOInput eventInPast = new EventDTOInput(
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
        EventDTOInput eventInPast = new EventDTOInput(
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
    void shouldPastValidationWhenEventIsInThePassAndCompleted() {
        //Arrange
        EventDTOInput eventInPast = new EventDTOInput(
                "meting",
                Instant.parse("1999-01-01T00:00:00.00Z"),
                60,
                "description of event",
                EventStatus.COMPLETED,
                null);

        //Action
        Optional<EventConstraint> rejectedCondition = validator.newEventTimeCheck(eventInPast);

        //Assert
        assertTrue(rejectedCondition.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenEventIsInFutureAndCompleted() {
        //Arrange
        EventDTOInput eventInFuture = new EventDTOInput(
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
    void shouldPastValidationWhenEventIsInFuturAndPlanned() {
        //Arrange
        EventDTOInput eventInFuture = new EventDTOInput(
                "meting",
                Instant.parse("2050-01-01T00:00:00.00Z"),
                60,
                "description of event",
                EventStatus.PLANNED,
                null);

        //Action
        Optional<EventConstraint> rejectedCondition = validator.newEventTimeCheck(eventInFuture);

        //Assert
        assertTrue(rejectedCondition.isEmpty());
    }

    @Test
    void shouldPastValidationWhenEventIsNowAndIsPlanned() {
        //Arrange
        EventDTOInput eventInNow = new EventDTOInput(
                "meting",
                // the date
                Instant.parse("2020-01-01T00:10:00.00Z"),
                60,
                "description of event",
                EventStatus.PLANNED,
                null);


        //Action
        Optional<EventConstraint> rejectedCondition = validator.newEventTimeCheck(eventInNow);

        //Assert
        assertTrue(rejectedCondition.isEmpty());
    }

}
