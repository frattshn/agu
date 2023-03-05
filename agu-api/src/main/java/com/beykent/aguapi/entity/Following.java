package com.beykent.aguapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
