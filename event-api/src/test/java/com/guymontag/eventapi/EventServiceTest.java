package com.guymontag.eventapi;

import com.guymontag.eventapi.dao.EventDAO;
import com.guymontag.eventapi.dto.response.EventDTOOutPut;
import com.guymontag.eventapi.exception.*;
import com.guymontag.eventapi.dto.response.EventDTOInput;
import com.guymontag.eventapi.entity.Event;
import com.guymontag.eventapi.service.EventServiceImpl;
import com.guymontag.eventapi.util.Convertor;
import com.guymontag.eventapi.util.EventStatus;
import com.guymontag.eventapi.util.Page;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventDAO eventDAO;

    @Mock
    private Convertor convertor;

    @InjectMocks
    private EventServiceImpl eventServiceImpl;

    // Test getEvent
    @Test
    void shouldReturnExceptionWhenEventNotFound() {

        // Arrange
        Long eventId = 30L;
        when(eventDAO.findById(eventId)).thenReturn(Optional.empty());

        // Action
        EventNotFoundException eventNotFoundExceptionResult =
                assertThrows(EventNotFoundException.class, () -> eventServiceImpl.getEvent(eventId));

        // Assert
        assertEquals("Event not found", eventNotFoundExceptionResult.getMessage());
        verify(eventDAO).findById(eventId);
    }

    @Test
    void shouldReturnExceptionWhenEventIdIsSmallerThenZero() {

        //Arrange
        Long eventId = -23L;

        //Action
        IdOutOfBoundException idOutOfBoundException =
                assertThrows(IdOutOfBoundException.class, () -> eventServiceImpl.getEvent(eventId));

        //Assert
        assertEquals("EventId out of bound", idOutOfBoundException.getMessage());

        verify(eventDAO, times(0)).findById(eventId);

    }

    @Test
    void shouldReturnExceptionWhenEventIdIsNull() {

        //Arrange
        IdValueNullException idValueNullExceptionExpected = new IdValueNullException("EventId has null value");

        Long eventIdNull = null;

        //Action
        IdValueNullException idValueNullExceptionResult =
                assertThrows(IdValueNullException.class, () -> eventServiceImpl.getEvent(eventIdNull));

        //Assert
        assertEquals(idValueNullExceptionExpected.getMessage(), idValueNullExceptionResult.getMessage());
    }

    @Test
    void shouldReturnEventDTOWhenEventExist() {

        //Arrange
        Event eventExpected = new Event(
                "meting",
                Instant.now(),
                60,
                Instant.now(),
                "description of event",
                EventStatus.IN_PROGRESS,
                "lausanne");

        long eventId = 42L;

        eventExpected.setEventId(eventId);

        EventDTOInput eventDTOInputExpected = new EventDTOInput(
                eventExpected.getName(),
                eventExpected.getStartOfEvent(),
                eventExpected.getDuration(),
                eventExpected.getDescription(),
                eventExpected.getStatus(),
                eventExpected.getLocation());

        when(eventDAO.findById(eventId)).thenReturn(Optional.of(eventExpected));

        when(convertor.convertEventToDTO(eventExpected)).thenReturn(eventDTOInputExpected);

        // Action
        EventDTOInput eventDTOInputResult = eventServiceImpl.getEvent(eventId);

        // Assert
        assertEquals(eventDTOInputExpected, eventDTOInputResult);
        verify(eventDAO).findById(eventId);
    }

    // Test GetEventPage
    @Test
    void shouldReturnEventDTOPageWhenPageNumberExist() {

        // Arrange
        List<EventDTOInput> sendEventDTOInputs = new ArrayList<>();

        int maxSize = 20;

        Long totalElements = 200L;
        for (int i = 0; i <= maxSize - 1; i++) {
            EventDTOInput eventDTOInput = new EventDTOInput("meting",
                    Instant.now(),
                    60,
                    "description of event",
                    EventStatus.IN_PROGRESS,
                    "lausanne");
            sendEventDTOInputs.add(eventDTOInput);
        }

        List<Event> foundedEvent = new ArrayList<>();

        for (int i = 0; i <= maxSize - 1; i++) {
            Event event = new Event(
                    "meting",
                    Instant.now(),
                    60,
                    Instant.now(),
                    "description of event",
                    EventStatus.IN_PROGRESS,
                    "lausanne");
            event.setEventId((long) i);
            foundedEvent.add(event);
        }

        int pageNumber = 0;

        Page<EventDTOInput> eventPageExpected = new Page<EventDTOInput>(sendEventDTOInputs, pageNumber, maxSize, totalElements);

        when(eventDAO.getEventPage(pageNumber, maxSize)).thenReturn(foundedEvent);

        when(eventDAO.getNumberOfEvent()).thenReturn(totalElements);

        when(convertor.convertEventsToDTOs(foundedEvent)).thenReturn(sendEventDTOInputs);

        //Action
        Page<EventDTOInput> eventPageResult = eventServiceImpl.getEventPage(pageNumber, maxSize);

        //Assert
        assertEquals(eventPageExpected.getSendElementDTOs(), eventPageResult.getSendElementDTOs());

        assertEquals(eventPageExpected.getPageNumber(), eventPageResult.getPageNumber());

        verify(eventDAO, times(1)).getNumberOfEvent();
    }

    @Test
    void shouldReturnExceptionWhenMaxSizeIsBiggerThan100() {

        //Arrange
        int notAcceptedMaxSize = 101;

        int pagenumber = 0;

        MaxSizeException maxSizeExceptionExpected = new MaxSizeException("MaxSize is bigger than limit 100");

        //Action
        MaxSizeException maxSizeExceptionResult = assertThrows(MaxSizeException.class, () -> eventServiceImpl.getEventPage(pagenumber, notAcceptedMaxSize));

        //Assert
        assertEquals(maxSizeExceptionExpected.getClass(), maxSizeExceptionResult.getClass());

        assertEquals(maxSizeExceptionExpected.getMessage(), maxSizeExceptionResult.getMessage());

        verify(eventDAO, never()).getEventPage(pagenumber, notAcceptedMaxSize);
    }

    @Test
    void shouldThrowExceptionWhenPageNumberIsNegative() {

        //Arrange
        int negativePageNumber = -1;

        int maxSize = 20;

        NegativePageNumberException negativePageNumberExceptionExpected = new NegativePageNumberException("PageNumber is negative");

        //Action
        NegativePageNumberException negativePageNumberExceptionResult =
                assertThrows(NegativePageNumberException.class, () -> eventServiceImpl.getEventPage(negativePageNumber, maxSize));

        //Assert
        assertEquals(negativePageNumberExceptionExpected.getClass(), negativePageNumberExceptionResult.getClass());

        assertEquals(negativePageNumberExceptionExpected.getMessage(), negativePageNumberExceptionResult.getMessage());

        verify(eventDAO, never()).getEventPage(negativePageNumber, maxSize);
    }

    @Test
    void shouldReturnCreatedEventWhenNewEventIsValid() {

        //Arrange
        Event inputEventExpected = new Event(
                "meting",
                Instant.parse("1980-04-09T10:15:30.00Z"),
                60,
                Instant.parse("1980-04-09T10:15:30.00Z"),
                "description of event",
                EventStatus.IN_PROGRESS,
                "lausanne");

        Event eventOutputExpected = new Event(
                "meting",
                inputEventExpected.getStartOfEvent(),
                60,
                inputEventExpected.getDateOfCreation(),
                "description of event",
                EventStatus.IN_PROGRESS,
                "lausanne");

        long eventId = 42L;

        inputEventExpected.setEventId(eventId);
        eventOutputExpected.setEventId(eventId);

        EventDTOInput eventDTOInputInputExpected = new EventDTOInput(
                inputEventExpected.getName(),
                inputEventExpected.getStartOfEvent(),
                inputEventExpected.getDuration(),
                inputEventExpected.getDescription(),
                inputEventExpected.getStatus(),
                inputEventExpected.getLocation());

        EventDTOOutPut outputEventDTOInputExpected = new EventDTOOutPut(
                inputEventExpected.getEventId(),
                inputEventExpected.getName(),
                inputEventExpected.getStartOfEvent(),
                inputEventExpected.getDuration(),
                inputEventExpected.getDescription(),
                inputEventExpected.getStatus(),
                inputEventExpected.getLocation());


        when(eventDAO.addEvent(inputEventExpected)).thenReturn(eventOutputExpected);

        when(convertor.convertDTOToEvent(eventDTOInputInputExpected)).thenReturn(inputEventExpected);

        when(convertor.convertEventToDTO(eventOutputExpected)).thenReturn(outputEventDTOInputExpected);

        //Action
        EventDTOInput eventResult = eventServiceImpl.addEvent(eventDTOInputInputExpected);

        //Assert
        assertEquals(outputEventDTOInputExpected, eventResult);

        verify(convertor, times(1)).convertDTOToEvent(eventDTOInputInputExpected);

        verify(convertor, times(1)).convertEventToDTO(eventOutputExpected);

    }

    @Test
    void shouldThrowExceptionWhenEventAlreadyExist() {

        //Arrange
        AlreadyExistEventException alreadyExistEventExceptionExpected = new AlreadyExistEventException("Event already exist");

        Event inputEvent = new Event(
                "meeting",
                Instant.parse("1980-04-09T10:15:30.00Z"),
                60,
                Instant.parse("1980-04-09T10:15:30.00Z"),
                "description of event",
                EventStatus.IN_PROGRESS,
                "Lausanne");

        EventDTOInput inputEventDTOInput = new EventDTOInput( "meeting",
                Instant.parse("1980-04-09T10:15:30.00Z"),
                60,
                "description of event",
                EventStatus.IN_PROGRESS,
                "Lausanne");

        when(eventDAO.eventExistsByBusinessKey(inputEventDTOInput.getName(), inputEventDTOInput.getStartOfEvent())).thenReturn(true);

        //Action

        AlreadyExistEventException alreadyExistEventExceptionResult = assertThrows(AlreadyExistEventException.class, () -> eventServiceImpl.addEvent(inputEventDTOInput));

        //Assert

        assertEquals(alreadyExistEventExceptionExpected.getMessage(), alreadyExistEventExceptionResult.getMessage());
        verify(eventDAO, never()).addEvent(inputEvent);
        verify(eventDAO,times(1)).eventExistsByBusinessKey(inputEventDTOInput.getName(), inputEventDTOInput.getStartOfEvent());

    }
}
