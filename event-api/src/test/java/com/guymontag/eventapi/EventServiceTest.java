package com.guymontag.eventapi;

import com.guymontag.eventapi.dao.EventDAO;
import com.guymontag.eventapi.exception.EventDTONullValueException;
import com.guymontag.eventapi.exception.EventNotFoundException;
import com.guymontag.eventapi.exception.IdOutOfBoundException;
import com.guymontag.eventapi.model.dto.EventDTO;
import com.guymontag.eventapi.model.entity.Event;
import com.guymontag.eventapi.exception.IdValueNullException;
import com.guymontag.eventapi.service.EventServiceImpl;
import com.guymontag.eventapi.util.Convertor;
import com.guymontag.eventapi.util.EventStatus;
import com.guymontag.eventapi.util.validator.ValidatorImpl;
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
        IdValueNullException idValueNullExceptionExcepted = new IdValueNullException("EventId has null value");

        Long eventIdNull = null;

        //Action
        IdValueNullException idValueNullExceptionResult =
                assertThrows(IdValueNullException.class, () -> eventServiceImpl.getEvent(eventIdNull));

        //Assert
        assertEquals(idValueNullExceptionExcepted.getMessage(), idValueNullExceptionResult.getMessage());
    }

    @Test
    void shouldReturnEventDTOWhenEventExist() {

        //Arrange
        Event eventExcepted = new Event(
                "meting",
                Instant.now(),
                60,
                Instant.now(),
                "description of event",
                EventStatus.IN_PROGRESS,
                "lausanne");

        long eventId = 42L;

        eventExcepted.setEventId(eventId);

        EventDTO eventDTOExcepted = new EventDTO(
                eventExcepted.getName(),
                eventExcepted.getStartOfEvent(),
                eventExcepted.getDuration(),
                eventExcepted.getDescription(),
                eventExcepted.getStatus(),
                eventExcepted.getLocation());

        when(eventDAO.findById(eventId)).thenReturn(Optional.of(eventExcepted));

        when(convertor.convertEventToDTO(eventExcepted)).thenReturn(eventDTOExcepted);

        // Action
        EventDTO eventDTOResult = eventServiceImpl.getEvent(eventId);

        // Assert
        assertEquals(eventDTOExcepted, eventDTOResult);
        verify(eventDAO).findById(eventId);
    }

    // Test GetEventPage
    @Test
    void shouldReturnEventDTOPageWhenPageNumberExist() {

        // Arrange
        List<EventDTO> sendEventDTOs = new ArrayList<>();


        for (int i = 0; i <= 19; i++) {
            EventDTO eventDTO = new EventDTO("meting",
                    Instant.now(),
                    60,
                    "description of event",
                    EventStatus.IN_PROGRESS,
                    "lausanne");
            sendEventDTOs.add(eventDTO);
        }

        List<Event> foundedEvent = new ArrayList<>();

        for (int i = 0; i <= 19; i++) {
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

        Page<EventDTO> eventPageExcepted = new Page<EventDTO>(sendEventDTOs, pageNumber);

        when(eventDAO.getEventPage(pageNumber)).thenReturn(foundedEvent);

        when(convertor.convertEventsToDTOs(foundedEvent)).thenReturn(sendEventDTOs);

        //Action
        Page<EventDTO> eventPageResult = eventServiceImpl.getEventPage(0);

        //Assert
        assertEquals(eventPageExcepted.getEvents(), eventPageResult.getEvents());
        assertEquals(eventPageExcepted.getPageNumber(),eventPageResult.getPageNumber());
    }

    @Test
    void shouldReturnExceptionWhenPageNumberIsSmallerThanZero() {

        //Arrange
        int smallerThenZeroPageNumber = -1;

        EventPageNumberSmallerThanZeroException eventPageNumberSmallerThanZeroExceptionExcepted = new EventPageNumberSmallerThanZeroException("Page number smaller then 0");

        //Action
        EventPageNumberSmallerThanZeroException eventPageNumberSmallerThanZeroExceptionResult =
                assertThrows(EventPageNumberSmallerThanZeroException.class, () -> eventServiceImpl.getEventPage(smallerThenZeroPageNumber));

        //Assert
        assertEquals(eventPageNumberSmallerThanZeroExceptionExcepted.getMessage(), eventPageNumberSmallerThanZeroExceptionResult.getMessage());

        verify(eventDAO, never()).getEventPage(smallerThenZeroPageNumber);
    }
}
