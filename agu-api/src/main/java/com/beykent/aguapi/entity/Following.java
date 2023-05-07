package com.beykent.aguapi.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "following_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
