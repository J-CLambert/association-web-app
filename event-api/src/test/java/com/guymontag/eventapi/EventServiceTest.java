package com.guymontag.eventapi;

import com.guymontag.eventapi.dao.EventDAO;
import com.guymontag.eventapi.exception.EventNotFoundException;
import com.guymontag.eventapi.exception.IdOutOfBoundException;
import com.guymontag.eventapi.model.Event;
import com.guymontag.eventapi.service.EventServiceImpl;
import com.guymontag.eventapi.util.EventStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventDAO eventDAO;

    @InjectMocks
    private EventServiceImpl eventServiceImpl;

    @Test
    void shouldReturnEventWhenEventExist() {

        // Arrange
        Event exceptedEvent = new Event(
                "meting",
                Instant.now(),
                120,
                Instant.now(),
                "description of event",
                EventStatus.IN_PROGRESS,
                "lausanne");

        long eventId = 42L;

        exceptedEvent.setEventId(eventId);

        when(eventDAO.findById(eventId)).thenReturn(Optional.of(exceptedEvent));

        // Action
        Event eventResult = eventServiceImpl.getEvent(eventId);

        // Assert
        assertEquals(exceptedEvent, eventResult);
        verify(eventDAO).findById(eventId);
    }

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
        assertEquals("eventId out of bound", idOutOfBoundException.getMessage());

        verify(eventDAO, times(0)).findById(eventId);

    }
}
