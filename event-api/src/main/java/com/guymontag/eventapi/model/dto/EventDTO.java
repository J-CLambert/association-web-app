package com.guymontag.eventapi.model.dto;

import com.guymontag.eventapi.exception.EventNullValueException;
import com.guymontag.eventapi.model.entity.Event;
import com.guymontag.eventapi.util.EventStatus;

import java.time.Instant;

public class EventDTO {

    private String name;

    private Instant startOfEvent;

    private int duration;

    private String description;

    private EventStatus status;

    private String location;

    public EventDTO(String name, Instant startOfEvent, int duration, String description, EventStatus status, String location) {
        this.name = name;
        this.startOfEvent = startOfEvent;
        this.duration = duration;
        this.description = description;
        this.status = status;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartOfEvent() {
        return startOfEvent;
    }

    public void setStartOfEvent(Instant startOfEvent) {
        this.startOfEvent = startOfEvent;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public EventDTO copy() {
        return new EventDTO(
                this.getName(),
                this.getStartOfEvent(),
                this.getDuration(),
                this.getDescription(),
                this.getStatus(),
                this.getLocation());
    }
}
