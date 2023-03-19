package com.beykent.aguapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity(name = "post")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "postedTime", "user_id" }) })
@Data
public class Post {

    public static final Boolean PUBLIC_POST = Boolean.TRUE;
    public static final Boolean PRIVATE_POST = Boolean.FALSE;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String text;

    private boolean isPublic;
    
    private LocalDate postedTime;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "post_tag",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") }
    )
    private Set<Tag> tags;

    @ManyToOne
    @JoinColumn(name = "mood_id")
    private Mood mood;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Like> likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Report> reports;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Image> images;

}
