package com.youtube.service;

import com.youtube.dto.subscription.SubscriptionCreateDTO;
import com.youtube.entity.SubscriptionEntity;
import com.youtube.enums.GeneralStatusEnum;
import com.youtube.exception.ItemNotFoundException;
import com.youtube.repository.ChannelRepository;
import com.youtube.repository.SubscriptionRepository;
import com.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private ChannelRepository channelRepository;

    public String create(SubscriptionCreateDTO dto) {
        Integer currentProfileId = SpringSecurityUtil.getCurrentProfileId();
        if(!channelRepository.existsByIdAndVisibleTrue(dto.getChannelId())){
            throw new ItemNotFoundException("Channel not found");
        }

        SubscriptionEntity subscription = subscriptionRepository.getByProfileIdAndChannelId(currentProfileId, dto.getChannelId());

        if(subscription == null){
            subscription = new SubscriptionEntity();
            subscription.setProfileId(currentProfileId);
            subscription.setChannelId(dto.getChannelId());
            subscription.setStatus(GeneralStatusEnum.ACTIVE);
            subscription.setNotification(dto.getType());
            subscription.setSubscribeDate(LocalDateTime.now());
        }
        else {
            if(subscription.getUnsubscribeDate() == null){
                subscription.setUnsubscribeDate(LocalDateTime.now());
            }
            else {
                if(subscription.getSubscribeDate().isBefore(subscription.getUnsubscribeDate())){
                    subscription.setSubscribeDate(LocalDateTime.now());
                    subscription.setUnsubscribeDate(null);
                }
                else {
                    subscription.setUnsubscribeDate(LocalDateTime.now());
                }
            }
        }

        subscriptionRepository.save(subscription);

        return "Done";
    }
}
