package com.youtube.service;

import com.youtube.dto.profile.ProfileChangePasswordDTO;
import com.youtube.entity.ProfileEntity;
import com.youtube.exception.AppBadException;
import com.youtube.exception.ItemNotFoundException;
import com.youtube.repository.ProfileRepository;
import com.youtube.util.SpringSecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public String changePassword(ProfileChangePasswordDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findById(SpringSecurityUtil.getCurrentProfileId());
        if(optional.isEmpty()){
            throw new ItemNotFoundException("User not found");
        }
        ProfileEntity profile = optional.get();
        if(!passwordEncoder.matches(dto.getOldPassword(), profile.getPassword())){
            throw new AppBadException("Current Password is wrong!");
        }
        profile.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        profileRepository.save(profile);

        return "Successfully changed";
    }
}
