package com.youtube.entity;

import com.youtube.enums.ProfileRoleEnum;
import com.youtube.enums.ProfileStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "profile")
@Getter
@Setter
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "photo_id")
    private String photoId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private AttachEntity photo;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ProfileRoleEnum role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProfileStatusEnum status;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime  createdDate;

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;
}
