package com.example.biblio.controller;

import com.example.biblio.obj.Book;
import com.example.biblio.obj.Event;
import com.example.biblio.obj.User;
import com.example.biblio.service.BookService;
import com.example.biblio.service.EventService;
import com.example.biblio.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private BookService bookService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    // Events API
    @GetMapping("/events")
    public List<Event> getAllEvents(@RequestParam(required = false) String place,
                                    @RequestParam(required = false) String ddate) {
        Date date = ddate != null ? Date.valueOf(ddate) : null;
        return eventService.searchEvent(place, date);
    }

    @PostMapping("/events")
    public Event createEvent(@RequestBody Event event) {
        return eventService.save_event(event);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.get_event(id);
        return ResponseEntity.ok(event);
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<Event> updateEvent(@RequestBody Event eventDetails) {
        Event updatedEvent = eventService.save_event(eventDetails);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.delete_event(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/books")
    public Book createBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.get(id);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody Book bookDetails) {
        Book updatedBook = bookService.save(bookDetails);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Users API
    @GetMapping("/users")
    public List<User> getAllUsers(@RequestParam(required = false) String keyword) {
        return keyword != null ? userService.findByColumn(keyword) : userService.listAll(null);
    }

    @PostMapping("/users")
    public User createUser (@RequestBody User user) {
        return userService.save_user(user);
    }


}