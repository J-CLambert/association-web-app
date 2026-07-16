package com.guymontag.eventapi;

import com.guymontag.eventapi.view.dao.EventDAO;
import com.guymontag.eventapi.exception.*;
import com.guymontag.eventapi.model.dto.EventDTO;
import com.guymontag.eventapi.model.entity.Event;
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

        EventDTO eventDTOExpected = new EventDTO(
                eventExpected.getName(),
                eventExpected.getStartOfEvent(),
                eventExpected.getDuration(),
                eventExpected.getDescription(),
                eventExpected.getStatus(),
                eventExpected.getLocation());

        when(eventDAO.findById(eventId)).thenReturn(Optional.of(eventExpected));

        when(convertor.convertEventToDTO(eventExpected)).thenReturn(eventDTOExpected);

        // Action
        EventDTO eventDTOResult = eventServiceImpl.getEvent(eventId);

        // Assert
        assertEquals(eventDTOExpected, eventDTOResult);
        verify(eventDAO).findById(eventId);
    }

    // Test GetEventPage
    @Test
    void shouldReturnEventDTOPageWhenPageNumberExist() {

        // Arrange
        List<EventDTO> sendEventDTOs = new ArrayList<>();

        int maxSize = 20;

        Long totalElements = 200L;
        for (int i = 0; i <= maxSize - 1; i++) {
            EventDTO eventDTO = new EventDTO("meting",
                    Instant.now(),
                    60,
                    "description of event",
                    EventStatus.IN_PROGRESS,
                    "lausanne");
            sendEventDTOs.add(eventDTO);
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

        Page<EventDTO> eventPageExpected = new Page<EventDTO>(sendEventDTOs, pageNumber, maxSize, totalElements);

        when(eventDAO.getEventPage(pageNumber, maxSize)).thenReturn(foundedEvent);

        when(eventDAO.getNumberOfEvent()).thenReturn(totalElements);

        when(convertor.convertEventsToDTOs(foundedEvent)).thenReturn(sendEventDTOs);

        //Action
        Page<EventDTO> eventPageResult = eventServiceImpl.getEventPage(pageNumber, maxSize);

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
        eventOutputExpected.setEventId(eventId);

        EventDTO eventDTOInputExpected = new EventDTO(
                inputEventExpected.getName(),
                inputEventExpected.getStartOfEvent(),
                inputEventExpected.getDuration(),
                inputEventExpected.getDescription(),
                inputEventExpected.getStatus(),
                inputEventExpected.getLocation());

        EventDTO outputEventDTOExpected = new EventDTO(
                eventId,
                inputEventExpected.getName(),
                inputEventExpected.getStartOfEvent(),
                inputEventExpected.getDuration(),
                inputEventExpected.getDescription(),
                inputEventExpected.getStatus(),
                inputEventExpected.getLocation());


        when(eventDAO.addEvent(inputEventExpected)).thenReturn(eventOutputExpected);

        when(convertor.convertDTOToEvent(eventDTOInputExpected)).thenReturn(inputEventExpected);

        when(convertor.convertEventToDTO(eventOutputExpected)).thenReturn(outputEventDTOExpected);

        //Action
        EventDTO eventResult = eventServiceImpl.addEvent(eventDTOInputExpected);

        //Assert
        assertEquals(outputEventDTOExpected, eventResult);

        verify(convertor, times(1)).convertDTOToEvent(eventDTOInputExpected);
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

        EventDTO inputEventDTO = new EventDTO( "meeting",
                Instant.parse("1980-04-09T10:15:30.00Z"),
                60,
                "description of event",
                EventStatus.IN_PROGRESS,
                "Lausanne");

        when(eventDAO.eventExistsByBusinessKey(inputEventDTO.getName(),inputEventDTO.getStartOfEvent())).thenReturn(true);

        //Action

        AlreadyExistEventException alreadyExistEventExceptionResult = assertThrows(AlreadyExistEventException.class, () -> eventServiceImpl.addEvent(inputEventDTO));

        //Assert

        assertEquals(alreadyExistEventExceptionExpected.getMessage(), alreadyExistEventExceptionResult.getMessage());
        verify(eventDAO, never()).addEvent(inputEvent);
        verify(eventDAO,times(1)).eventExistsByBusinessKey(inputEventDTO.getName(),inputEventDTO.getStartOfEvent());

    }

            /*
            Fields that are NOT allowed to be empty:

    name - string
    date of event
    duration(minutes) must be 0 by default
    description - string
    statut - Enum: IN_PROGRESSE by default

 To be able to create a new event
Pass test the function addEvent for following case:
newEvent contain an eventId != 0
newEvent already exists in DB -> error response 409 conflict
some field are empty
the date is not in paste
the name doesn't already exist
duration < int MAX and duration > 0
DB is not accessible -> 500
             */
}
