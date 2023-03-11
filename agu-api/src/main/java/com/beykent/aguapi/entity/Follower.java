package com.beykent.aguapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "follower")
@Data
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follower_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
