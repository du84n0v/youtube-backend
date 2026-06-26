package com.youtube.entity;

import com.youtube.enums.ReportTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Getter
@Setter
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id",  insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "video_type")
    @Enumerated(EnumType.STRING)
    private ReportTypeEnum videoType;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime  createdDate;
}
