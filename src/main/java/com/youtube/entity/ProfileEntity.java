package com.youtube.entity;

import com.youtube.enums.ProfileStatus;
import com.youtube.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private AttachEntity photo;

    @OneToMany(mappedBy = "profile")
    private List<ProfileRoleEntity> profileList;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
}
