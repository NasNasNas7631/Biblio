package com.example.biblio.service;

import java.util.List;
import java.util.Optional;

import com.example.biblio.obj.Book;
import com.example.biblio.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service

public class BookService {
    @Autowired
    private BookRepository repo;
    public List<Book> listAll(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }

    public Book save(Book book) {
        return repo.save(book);
    }

    public Book get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<Book> findByColumn(String keyword, String column) {
        switch (column) {
            case "id":
                return repo.search(keyword);
            case "name":
                return repo.findByName(keyword);
            case "author":
                return repo.findByAuthor(keyword);
            case "year":
                return repo.findByYear(Integer.parseInt(keyword));
        }
        return List.of();
    }

    public Book updateStatus(Long bookId, String status) {
        Optional<Book> optionalBook = repo.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setStatus(status);
            return repo.save(book);
        }
        return null;
    }


}