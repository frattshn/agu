package com.beykent.aguapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.mapping.ToOne;

@Entity(name = "image")
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
