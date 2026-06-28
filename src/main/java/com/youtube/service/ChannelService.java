package com.youtube.service;

import com.youtube.dto.channel.*;
import com.youtube.entity.AttachEntity;
import com.youtube.entity.ChannelEntity;
import com.youtube.entity.ProfileEntity;
import com.youtube.enums.GeneralStatusEnum;
import com.youtube.exception.AppBadException;
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



    public ChannelResponseDTO create(ChannelRequestDTO requestDTO) {
        ChannelEntity channelEntity = getChannelByName(requestDTO.getName());
        if (channelEntity != null) {
            throw new ChannelWithSuchNameExistsException("Channel with such name already exists");

        }

        ProfileEntity profile = getProfileById(SpringSecurityUtil.getCurrentProfileId());
        AttachEntity photo = getAttachById(requestDTO.getPhotoId());
        AttachEntity banner = getAttachById(requestDTO.getBannerId());

        ChannelEntity channel = new ChannelEntity();
        channel.setName(requestDTO.getName());
        channel.setOwner(profile);
        channel.setProfileId(profile.getId());
        channel.setDescription(requestDTO.getDescription());
        channel.setPhotoId(requestDTO.getPhotoId());
        channel.setPhoto(photo);
        channel.setBannerId(requestDTO.getBannerId());
        channel.setBanner(banner);
        channel.setStatus(GeneralStatusEnum.ACTIVE);
        channelRepository.save(channel);
        return toResponseDTO(channel);


    }
    public ChannelUpdateDTO update(ChannelUpdateDTO channelUpdateDTO) {
        ProfileEntity profile = getProfileById(SpringSecurityUtil.getCurrentProfileId());
        ChannelEntity channel = getChannelByItsId(channelUpdateDTO.getId());

        if (!(channel.getProfileId().equals(profile.getId()))) {
            throw new AppBadException("This not your channel");
        }
        AttachEntity photo = getAttachById(channelUpdateDTO.getPhotoId());
        AttachEntity banner = getAttachById(channelUpdateDTO.getBannerId());



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
    public ChannelInfoDTO changeChannelStatusByAdmin(ChangeChannelStatusDTO dto){
        ChannelEntity channelEntity =getJustChannelById(dto.getChannelId());
        if(dto.getStatus().equals(GeneralStatusEnum.BLOCKED) &&
                channelEntity.getStatus().equals(GeneralStatusEnum.BLOCKED)){
            return toDTO(channelEntity);
        }else if(
                dto.getStatus().equals(GeneralStatusEnum.ACTIVE) &&
                        channelEntity.getStatus().equals(GeneralStatusEnum.ACTIVE)

        ){
            return toDTO(channelEntity);
        }
        channelEntity.setStatus(dto.getStatus());
        channelRepository.save(channelEntity);
        return toDTO(channelEntity);
    }
    public List<ChannelInfoDTO>GetUsersChannelsById(){
      Integer id=SpringSecurityUtil.getCurrentProfileId();
        List<ChannelEntity>  list= channelRepository.getChannelEntitiesByProfileId(id);
        List<ChannelInfoDTO> dtoList = list.stream()
                .map(e -> toDTO(e))
                .toList();
        return dtoList;
    }
    public ChannelResponseDTO getChannelById(String id){
        ChannelEntity channelEntity= getChannelByItsId(id);
        return toResponseDTO(channelEntity);
    }

    private ChannelInfoDTO toDTO(ChannelEntity e) {

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
    private ChannelEntity getChannelByItsId(String id) {
        ChannelEntity isExist = channelRepository.findByIdAndStatusIsActive(id).orElseThrow(() -> {
            throw new AppBadException("Channel not found");

        });
        return isExist;


    }
    private ChannelEntity getJustChannelById(String id) {
        return channelRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Channel not found");
        });
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
    private ChannelResponseDTO toResponseDTO(ChannelEntity requestDTO){

        return new ChannelResponseDTO(
                requestDTO.getId(),
                requestDTO.getName(),
                requestDTO.getDescription(),
                requestDTO.getProfileId(),
                requestDTO.getBannerId(),
                requestDTO.getPhotoId(),
                requestDTO.getCreatedDate(),
                requestDTO.getStatus()
                );

    }

    public boolean isProfileChannelOwner(Integer profileId, String channelId) {
        return channelRepository.findByIdAndProfileId(channelId, profileId) != null;
    }
}