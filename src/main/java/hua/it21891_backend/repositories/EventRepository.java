package hua.it21891_backend.repositories;

import hua.it21891_backend.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

//    public Optional<Event> findByName(String name);
    public Optional<Event> findByEventId(int id);

    public List<Event> findAll();

    public void deleteEventByEventId(int id);
}
