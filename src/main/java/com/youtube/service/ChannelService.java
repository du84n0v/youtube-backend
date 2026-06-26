package com.youtube.service;

import com.youtube.dto.channel.ChannelDTO;
import com.youtube.dto.channel.ChannelInfoDTO;
import com.youtube.dto.channel.ChannelUpdateDTO;
import com.youtube.entity.AttachEntity;
import com.youtube.entity.ChannelEntity;
import com.youtube.entity.ProfileEntity;
import com.youtube.enums.GeneralStatusEnum;
import com.youtube.exception.AppBadException;
import com.youtube.exception.ChannelException;
import com.youtube.exception.ChannelWithSuchNameExistsException;
import com.youtube.repository.AttachRepository;
import com.youtube.repository.ChannelRepository;
import com.youtube.repository.ProfileRepository;
import com.youtube.util.PageUtil;
import com.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service

public class ChannelService {

    @Autowired

    private ChannelRepository channelRepository;

    @Autowired

    private ProfileRepository profileRepository;

    @Autowired

    private AttachRepository attachRepository;


    public ChannelDTO create(ChannelDTO channelDTO) {
        ChannelEntity channelEntity = getChannelByName(channelDTO.getName());
        if (channelEntity != null) {
            throw new ChannelWithSuchNameExistsException("Channel with such name already exists");

        }
        ProfileEntity profile = getProfileById(SpringSecurityUtil.getCurrentProfileId());
        AttachEntity photo = getAttachById(channelDTO.getPhotoId());
        AttachEntity banner = getAttachById(channelDTO.getBannerId());

        ChannelEntity channel = new ChannelEntity();
        channel.setName(channelDTO.getName());
        channel.setOwner(profile);
        channel.setProfileId(profile.getId());
        channel.setDescription(channelDTO.getDescription());
        channel.setPhotoId(channelDTO.getPhotoId());
        channel.setPhoto(photo);
        channel.setBannerId(channelDTO.getBannerId());
        channel.setBanner(banner);
        channel.setStatus(GeneralStatusEnum.ACTIVE);
        channelRepository.save(channel);
        channelDTO.setId(channel.getId());
        return channelDTO;

    }
    public ChannelUpdateDTO update(ChannelUpdateDTO channelUpdateDTO) {
        ProfileEntity profile = getProfileById(SpringSecurityUtil.getCurrentProfileId());
        AttachEntity photo = getAttachById(channelUpdateDTO.getPhotoId());
        AttachEntity banner = getAttachById(channelUpdateDTO.getBannerId());
        ChannelEntity channel = getChannelById(channelUpdateDTO.getId());

        if (!(channel.getProfileId().equals((profile.getId()).toString()))) {
            throw new AppBadException("This not your channel");
        }

        if (!(channel.getName().equals(channelUpdateDTO.getName()))) {
            ChannelEntity channelEntity = getChannelByName(channelUpdateDTO.getName());
            if (channelEntity != null) {
                throw new ChannelWithSuchNameExistsException("Channel with such name already exists");

            }

        }
        channel.setName(channelUpdateDTO.getName());
        channel.setPhoto(photo);
        channel.setPhotoId(photo.getId());
        channel.setBanner(banner);
        channel.setBannerId(banner.getId());
        channel.setDescription(channelUpdateDTO.getDescription());
        channelRepository.save(channel);
        channelUpdateDTO.setId(channel.getId());
        return channelUpdateDTO;

    }
    public Page<ChannelInfoDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(PageUtil.page(page), size);
        Page<ChannelEntity> entities = channelRepository.pagination(pageable);

        List<ChannelEntity> entityList = entities.getContent();
        long totalElement= entities.getTotalElements();

        List<ChannelInfoDTO> dtos = new LinkedList<>();
        entityList.forEach(e -> dtos.add(toDTO(e)));
        return new PageImpl<>(dtos, pageable, totalElement);
    }
    public ChannelInfoDTO getChannelInfoById(String id){
        ChannelEntity channelEntity= getChannelById(id);


        return new ChannelInfoDTO(
                channelEntity.getId(),
                channelEntity.getName(),
                channelEntity.getDescription(),
                channelEntity.getBannerId(),
                channelEntity.getPhotoId(),
                channelEntity.getCreatedDate()
        );
    }
    public ChannelInfoDTO changeChannelStatusAdmin(String id){
        ChannelEntity channelEntity =getJustChannelById(id);
        if(channelEntity.getStatus().equals(GeneralStatusEnum.BLOCKED)){
            throw new ChannelException("Channel id already blocked");
        }
        channelEntity.setStatus(GeneralStatusEnum.BLOCKED);
        channelRepository.save(channelEntity);


        return toDTO(channelEntity);
    }
    public List<ChannelInfoDTO>GetUsersChannelsById(Integer id){
      List<ChannelEntity>  list= channelRepository.getChannelEntitiesByProfileId(id.toString());
        List<ChannelInfoDTO> dtoList = list.stream()
                .map(e -> toDTO(e))
                .toList();
        return dtoList;
    }

    private ChannelInfoDTO toDTO(ChannelEntity e) {
        if(e.getStatus().equals(GeneralStatusEnum.BLOCKED)){

            return new ChannelInfoDTO(
                    e.getId(),
                    e.getName(),
                    e.getDescription(),
                    e.getProfileId(),
                    e.getBannerId(),
                    e.getPhotoId(),
                    e.getCreatedDate(),
                    e.getStatus()
            );
        }
        return new ChannelInfoDTO(
                e.getId(),
                e.getName(),
                e.getDescription(),
                e.getProfileId(),
                e.getBannerId(),
                e.getPhotoId(),
                e.getCreatedDate()
        );
    }
    private ChannelEntity getChannelById(String id) {
        ChannelEntity isExist = channelRepository.findByIdAndStatusIsActive(id).orElseThrow(() -> {
            throw new AppBadException("Channel not found");

        });
        return isExist;


    }
    private ChannelEntity getJustChannelById(String id) {
        ChannelEntity isExist = channelRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Channel not found");

        });
        return isExist;


    }
    private ChannelEntity getChannelByName(String id) {
        return channelRepository.findByName(id).orElse(null);


    }


    private AttachEntity getAttachById(String photoId) {
        AttachEntity isExist = attachRepository.findById(photoId).orElseThrow(() -> {
            throw new AppBadException("Attach not found");

        });
        return isExist;


    }


    private ProfileEntity getProfileById(Integer id) {
        ProfileEntity isExist = profileRepository.findByIdAndStatusIsActive(id).orElseThrow(() -> {
            throw new AppBadException("Profile not found");

        });
        return isExist;


    }

}