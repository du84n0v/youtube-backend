package com.youtube.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class AttachEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "origin_name")
    private String originName;

    @Column(name = "size")
    private Long size;

    @Column(name = "type")
    private String type;

    @Column(name = "path")
    private String path;

    @Column(name = "duration")
    private Long duration;
}
