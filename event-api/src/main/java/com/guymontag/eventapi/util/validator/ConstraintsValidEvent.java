package com.guymontag.eventapi.util.validator;

import com.guymontag.eventapi.exception.ApiException;
import com.guymontag.eventapi.exception.PastEventPlannedException;
import com.guymontag.eventapi.model.dto.EventDTO;
import com.guymontag.eventapi.util.EventStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Configuration
public class ConstraintsValidEvent {

    private Clock clock;

    ConstraintsValidEvent(Clock clock) {
        this.clock = clock;
    }

    public List<EventConstraint> constraints = List.of(
            new EventConstraint(
                    eventDTO -> eventDTO.getStartOfEvent().isBefore(Instant.now(clock)) && (eventDTO.getStatus() == EventStatus.PLANNED),
                    () -> new PastEventPlannedException("Event cannot be planned and be in the past")),
            new EventConstraint(
                    eventDTO -> eventDTO.getStartOfEvent().isAfter(Instant.now(clock)) && (eventDTO.getStatus() == EventStatus.COMPLETED),
                    () -> new PastEventPlannedException("Event cannot be completed and be in the future"))
    );
}
