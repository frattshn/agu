package com.beykent.aguapi.entity;

import javax.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "saved_post")
@Data
public class SavedPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinColumn(name = "user_id")
    private List<User> user;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime savedTime;
}
