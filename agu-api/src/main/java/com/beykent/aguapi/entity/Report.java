package com.beykent.aguapi.entity;

import javax.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "report")
@Data
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    // bunu ekledim diyagramda yok
    private String reason;

    @ManyToOne
    @JoinColumn(name = "reported_user_id")
    private User reportedUser;

    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
