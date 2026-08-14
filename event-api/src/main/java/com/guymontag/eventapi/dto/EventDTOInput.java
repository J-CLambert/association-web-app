package com.guymontag.eventapi.dto;

import com.guymontag.eventapi.util.EventStatus;

import java.time.Instant;

public class EventDTOInput {

    private String name;

    private Instant startOfEvent;

    private int duration;

    private String description;

    private EventStatus status;

    private String location;

    public EventDTOInput(String name, Instant startOfEvent, int duration, String description, EventStatus status, String location) {
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

    public EventDTOInput copy() {
        return new EventDTOInput(
                this.getName(),
                this.getStartOfEvent(),
                this.getDuration(),
                this.getDescription(),
                this.getStatus(),
                this.getLocation());
    }
}
