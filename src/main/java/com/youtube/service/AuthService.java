package com.youtube.service;

import com.youtube.dto.auth.RegisterDTO;
import com.youtube.entity.ProfileEntity;
import com.youtube.enums.ProfileRoleEnum;
import com.youtube.enums.ProfileStatusEnum;
import com.youtube.exception.AppBadException;
import com.youtube.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(RegisterDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if(optional.isPresent()){
            if(optional.get().getStatus().equals(ProfileStatusEnum.IN_REGISTRATION)){
                profileRepository.delete(optional.get());
            }
            else {
                throw new AppBadException("User already exists");
            }
        }
        ProfileEntity profile = new ProfileEntity();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setEmail(dto.getEmail());
        profile.setPassword(passwordEncoder.encode(dto.getPassword()));
        profile.setRole(ProfileRoleEnum.ROLE_USER);
        profile.setStatus(ProfileStatusEnum.IN_REGISTRATION);

        profileRepository.save(profile);

        return "Code sent to your email. Please check email";
    }
}
