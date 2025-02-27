package br.com.nlw.events.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.nlw.events.model.Event;


public interface EventRepository extends CrudRepository <Event, Integer> {
    public Event findByPrettyname(String prettyname);
}
