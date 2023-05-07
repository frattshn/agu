package com.beykent.aguapi.entity;

import javax.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity(name = "tag")
@Data
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts;
}
