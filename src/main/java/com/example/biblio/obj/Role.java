package com.example.biblio.obj;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;


@Entity
@Setter
@Getter
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
