package com.guymontag.eventapi.dao;

import com.guymontag.eventapi.model.entity.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EventDAOImpl implements EventDAO {

    private EntityManager entityManager;

    @Autowired
    public EventDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Event> findById(Long eventId) {


        TypedQuery<Event> findByIdQuery = entityManager.createQuery("FROM Event WHERE id=:eventId", Event.class);

        findByIdQuery.setParameter("eventId", eventId);

        return findByIdQuery.getResultStream().findFirst();
    }

    @Override
    public Object getEventPage(int pageNumber) {
        return null;
    }
}
