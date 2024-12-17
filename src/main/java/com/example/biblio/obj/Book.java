package com.example.biblio.obj;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Setter
@Entity
@Getter
public class Book {
    private long Id;

    @NotBlank(message = "Название книги обязательно")
    private String name;

    @NotBlank(message = "Автор книги обязателен")
    private String author;

    private String publisher;

    private int year;

    private int count_pg;

    private int count_mpg;

    @NotBlank(message = "ISBN обязателен")
    private String ISBN;

    private String BBK;

    private String status;

    public Book(){ // Создаем метод
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return Id;
    }
}