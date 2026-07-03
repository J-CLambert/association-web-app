package com.guymontag.eventapi.view.dao;

import com.guymontag.eventapi.model.entity.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EventDAOImpl implements EventDAO {

    private static final Logger log = LoggerFactory.getLogger(EventDAOImpl.class);

    private EntityManager entityManager;

    @Autowired
    public EventDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Event> findById(Long eventId) {

        TypedQuery<Event> findByIdQuery = entityManager.createQuery("FROM Event WHERE id=:eventId", Event.class);

        findByIdQuery.setParameter("eventId", eventId);

        log.debug("request db with id {}", eventId);

        return findByIdQuery.getResultStream().findFirst();
    }

    @Override
    public List<Event> getEventPage(int pageNumber, int maxSize) {

        TypedQuery<Event> pageEventQ = entityManager.createQuery("FROM Event", Event.class);

        int offset = pageNumber * maxSize;

        pageEventQ.setFirstResult(offset);

        pageEventQ.setMaxResults(maxSize);

        log.debug("request db with all event offset at :{}", offset);

        return pageEventQ.getResultList();
    }

    @Override
    public Long getNumberOfEvent() {

        TypedQuery<Long> numberEventQ = entityManager.createQuery("SELECT COUNT(e) FROM Event e", Long.class);
        return numberEventQ.getSingleResult();
    }
}
