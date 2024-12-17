package com.example.biblio.repo;

import com.example.biblio.obj.Book;
import com.example.biblio.obj.BookOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {
    List<BookOrder> findByUsername(String username);
}