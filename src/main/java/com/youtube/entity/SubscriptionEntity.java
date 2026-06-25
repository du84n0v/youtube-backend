package com.youtube.entity;

import com.youtube.enums.GeneralStatusEnum;
import com.youtube.enums.NotificationTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscription")
@Getter
@Setter
public class SubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "channel_id")
    private String channelId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", updatable = false, insertable = false)
    private ChannelEntity channel;

    @Column(name = "subscribe_date")
    @CreationTimestamp
    private LocalDateTime subscribeDate;

    @Column(name = "unsubscribe_date")
    @UpdateTimestamp
    private LocalDateTime unsubscribeDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GeneralStatusEnum status;

    @Column(name = "notification")
    @Enumerated
    private NotificationTypeEnum  notification;

}
