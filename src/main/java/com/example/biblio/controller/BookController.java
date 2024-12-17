package com.example.biblio.controller;

import com.example.biblio.obj.Book;
import com.example.biblio.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/updateStatus")
    public ResponseEntity<String> updateStatus(@RequestBody Book request) {
        Book book = bookService.updateStatus( request.getId(), request.getStatus());
        if (book != null) {
            return ResponseEntity.ok("Статус книги успешно обновлен!");
        } else {
            return ResponseEntity.status(404).body("Книга не найдена.");
        }
    }
}