package com.youtube.entity;

import com.youtube.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profile_role")
@Getter
@Setter
public class ProfileRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false, nullable = false)
    private ProfileEntity profile;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
}
