package com.youtube.entity;

import com.youtube.enums.EmotionEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment_like")
@Getter
@Setter
public class CommentLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "comment_id")
    private Integer commentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", insertable = false, updatable = false)
    private CommentEntity comment;

    @Column(name = "created_date_time")
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @Column(name = "emotion")
    @Enumerated(EnumType.STRING)
    private EmotionEnum emotionEnum;
}
