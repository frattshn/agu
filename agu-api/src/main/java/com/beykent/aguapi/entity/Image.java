package com.beykent.aguapi.entity;

import javax.persistence.*;

import lombok.Data;

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
