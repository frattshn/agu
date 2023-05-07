package com.beykent.aguapi.entity;

import javax.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "notification")
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    // sender user  ?

    private  int notificationType;

    // mesaj string olmalı - message tipinde olmamalı
    private String notificationMessage;

    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
