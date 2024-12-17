package com.example.biblio.obj;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Setter
@Getter
@Table(name="bookorder")
public class BookOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private Long book_id;
    private String name;
    private String author;

    @Column(nullable = false, updatable = false)
    private Date ddate;

    @Column(nullable = false, updatable = false)
    private Date dueDate;

    public BookOrder() {
        this.ddate = Date.valueOf(LocalDateTime.now().toLocalDate());
        this.dueDate = Date.valueOf(LocalDateTime.now().plus(1, ChronoUnit.MONTHS).toLocalDate());
    }
}