package com.guymontag.eventapi;

import com.guymontag.eventapi.controller.EventController;
import com.guymontag.eventapi.dto.response.EventDTO;
import com.guymontag.eventapi.exception.EventNotFoundException;
import com.guymontag.eventapi.dto.response.EventDTOInput;
import com.guymontag.eventapi.service.EventService;
import com.guymontag.eventapi.util.EventStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;


    @Test
    void shouldReturnEventNotFoundExceptionWhenEventNotFound() {

        // Arrange
        Long notExistingEventId = 100000L;

        EventNotFoundException eventNotFoundExceptionExcepted = new EventNotFoundException("Event not found");

        when(eventService.getEvent(notExistingEventId)).thenThrow(eventNotFoundExceptionExcepted);

        //Action
        EventNotFoundException eventNotFoundExceptionResult =
                assertThrows(EventNotFoundException.class, () -> eventController.getEvent(notExistingEventId));

        //Assert
        assertEquals(eventNotFoundExceptionExcepted.getMessage(), eventNotFoundExceptionResult.getMessage());

        verify(eventService, times(1)).getEvent(notExistingEventId);
    }

    @Test
    void shouldReturnEventDTOWhenEventIdExist() {

        //Arrange
        Long existingEventId = 42L;

        EventDTOInput eventDTOInputExcepted = new EventDTOInput(
                "meting",
                Instant.now(),
                60,
                "description of event",
                EventStatus.IN_PROGRESS,
                "lausanne");

        when(eventService.getEvent(existingEventId)).thenReturn(eventDTOInputExcepted);

        //Action

        EventDTO eventDTOInputResult = eventService.getEvent(existingEventId);

        //Assert
        assertEquals(eventDTOInputExcepted, eventDTOInputResult);
        verify(eventService, times(1)).getEvent(existingEventId);

    }

}
