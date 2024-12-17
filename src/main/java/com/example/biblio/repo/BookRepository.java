package com.example.biblio.repo;

import java.util.List;

import com.example.biblio.obj.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT p FROM Book p WHERE CAST(p.id AS string) LIKE %?1%")
    List<Book> search(String keyword);

    @Query("SELECT t FROM Book t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Book> findByName(@Param("name") String name);

    @Query("SELECT t FROM Book t WHERE LOWER(t.author) LIKE LOWER(CONCAT('%', :author, '%'))")
    List<Book> findByAuthor(@Param("author") String author);

    @Query("SELECT t FROM Book t WHERE t.year = :year")
    List<Book> findByYear(@Param("year") int year);

}
