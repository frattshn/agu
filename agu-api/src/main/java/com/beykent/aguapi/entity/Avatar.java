package com.beykent.aguapi.entity;

import jakarta.persistence.*;
import lombok.Data;
@Entity(name = "avatar")
@Data
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatar_id")
    private Long id;

    private String avatarUrl;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
