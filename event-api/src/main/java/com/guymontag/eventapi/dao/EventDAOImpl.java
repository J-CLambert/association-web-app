package com.guymontag.eventapi.dao;

import com.guymontag.eventapi.entity.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EventDAOImpl implements EventDAO {

    private EntityManager entityManager;

    @Override
    public Optional<Event> findById(Long eventId) {

        TypedQuery<Event> findByIdQuery = entityManager.createQuery("FROM Event WHERE id=:eventId", Event.class);

        findByIdQuery.setParameter("eventId", eventId);

        return Optional.ofNullable(findByIdQuery.getSingleResult());
    }
}
