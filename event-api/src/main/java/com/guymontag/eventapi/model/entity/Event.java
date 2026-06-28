package com.guymontag.eventapi.model.entity;


import com.guymontag.eventapi.util.EventStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Table(name = "events")
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_of_event")
    private Instant startOfEvent;

    @Column(name = "duration")
    private int duration;

    @Column(name = "date_of_creation")
    private Instant dateOfCreation;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EventStatus status;

    @Column(name = "location")
    private String location;


    public Event(String name, Instant startOfEvent, int duration, Instant dateOfCreation, String description, EventStatus status, String location) {
        this.name = name;
        this.startOfEvent = startOfEvent;
        this.duration = duration;
        this.dateOfCreation = dateOfCreation;
        this.description = description;
        this.status = status;
        this.location = location;
    }

    public Event(String name, Instant startOfEvent, int duration, String description, EventStatus status, String location) {
        this.name = name;
        this.startOfEvent = startOfEvent;
        this.duration = duration;
        this.description = description;
        this.status = status;
        this.location = location;
    }

    public Event() {
    }

    public Long getEventId() {
        return id;
    }

    public void setEventId(Long id) {
        this.id = id;
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

    public Instant getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Instant dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
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


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startOfEvent=" + startOfEvent +
                ", duration=" + duration +
                ", dateOfCreation=" + dateOfCreation +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return duration == event.duration && Objects.equals(id, event.id) && Objects.equals(name, event.name) && Objects.equals(startOfEvent, event.startOfEvent) && Objects.equals(dateOfCreation, event.dateOfCreation) && Objects.equals(description, event.description) && status == event.status && Objects.equals(location, event.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startOfEvent, duration, dateOfCreation, description, status, location);
    }
}
