package com.guymontag.eventapi.view.dao;

import com.guymontag.eventapi.model.entity.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
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
    public List<Event> getEventPage(int pageNumber, int maxSize) {

        TypedQuery<Event> pageEventQ = entityManager.createQuery("FROM Event", Event.class);

        int offset = pageNumber * maxSize;

        pageEventQ.setFirstResult(offset);

        pageEventQ.setMaxResults(maxSize);

        return pageEventQ.getResultList();
    }

    @Override
    public Long getNumberOfEvent() {

        TypedQuery<Long> numberEventQ = entityManager.createQuery("SELECT COUNT(e) FROM Event e", Long.class);

        return numberEventQ.getSingleResult();
    }

    @Override
    public Object eventExistsByBusinessKey(String name, Instant startOfEvent) {
        return null;
    }

    @Override
    public Event addEvent(Event inputEvent) {
        return null;
    }
}
