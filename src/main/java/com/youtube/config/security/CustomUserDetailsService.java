package com.youtube.config.security;

import com.youtube.entity.ProfileRoleEntity;
import com.youtube.enums.ProfileStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.youtube.entity.ProfileEntity;
import com.youtube.enums.ProfileRoleEnum;
import com.youtube.exception.ItemNotFoundException;
import com.youtube.repository.ProfileRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUsername: " + username);
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndStatus(username, ProfileStatusEnum.ACTIVE);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("User not found");
        }
        ProfileEntity profile = optional.get();
        List<ProfileRoleEnum> roleEnums = new ArrayList<>();
        for (ProfileRoleEntity role : profile.getProfileRoleList()) {
            roleEnums.add(role.getRole());
        }
        return new CustomUserDetails(
                profile.getId(),
                profile.getEmail(),
                profile.getPassword(),
                profile.getName(),
                profile.getSurname(),
                profile.getStatus(),
                roleEnums.getFirst()
        );
    }
}
