package com.example.biblio.service;

import java.sql.Date;
import java.util.List;

import com.example.biblio.obj.Event;
import com.example.biblio.repo.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service

public class EventService {
    @Autowired
    private EventRepository repo;
    public List<Event> listAll(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }

    public Event save_event(Event event) {return repo.save(event);
    }

    public Event get_event(Long id) {
        return repo.findById(id).get();
    }

    public void delete_event(Long id) {
        repo.deleteById(id);
    }
    public List<Event> searchEvent(String place, Date ddate) {
        if (place != null && ddate != null) {
            return repo.findByDateAndPlace(ddate, place);
        } else if (place != null && !place.isEmpty()) {
            return repo.findByPlace(place);
        } else if (ddate != null) {
            return repo.findByDate(ddate);
        }
        return repo.findAll();
    }

}