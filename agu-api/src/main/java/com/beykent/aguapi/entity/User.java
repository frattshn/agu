package com.beykent.aguapi.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity(name = "user")
@Data
public class User {
	
	public static final Integer USER_ACTIVE 	= Integer.valueOf(1);
	public static final Integer USER_INACTIVE 	= Integer.valueOf(0);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT(20)")
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "background_image_id")
    private Image backgroundImage;

    @Column(name = "name_surname", columnDefinition = "VARCHAR(255)")
    private String nameSurname;

    @Size(min = 3)
    @Column(name = "user_name", unique = true, columnDefinition = "VARCHAR(255)")
    private String username;

    @Column(name = "password", columnDefinition = "VARCHAR(255)")
    private String password;

    @Email
    @Column(name = "email", unique = true, columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(name = "bio_content", columnDefinition = "TEXT")
    private String bioContent;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "birthday_date", columnDefinition = "DATE")
    private LocalDate birthdayDate;

    @Column(name = "created_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdTime;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private List<Follower> followers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private List<Following> followings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private List<Like> likes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "senderUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;

}
