package com.guymontag.eventapi.dto.response;

import com.guymontag.eventapi.util.EventStatus;

import java.time.Instant;

public interface EventDTO {

    EventDTO copy();

    String getName();

    void setName(String name);

    Instant getStartOfEvent();

    void setStartOfEvent(Instant startOfEvent);

    int getDuration();

    void setDuration(int duration);

    String getDescription();

    void setDescription(String description);

    EventStatus getStatus();

    void setStatus(EventStatus status);

    String getLocation();

    void setLocation(String location);
}
