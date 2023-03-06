package com.beykent.aguapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String avatarUrl;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
