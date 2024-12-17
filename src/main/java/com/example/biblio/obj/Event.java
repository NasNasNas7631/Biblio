package com.example.biblio.obj;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


import java.sql.Date;

@Setter
@Entity
@Getter
public class Event {
    private long Id;
    private String name;
    private String type;
    private String place;
    private Date ddate;
    private String imageUrl;

    public Event(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return Id;
    }
}