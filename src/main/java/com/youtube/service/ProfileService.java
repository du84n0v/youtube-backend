package com.youtube.service;

import com.youtube.dto.AttachShortInfoDTO;
import com.youtube.dto.profile.*;
import com.youtube.entity.AttachEntity;
import com.youtube.entity.EmailHistoryEntity;
import com.youtube.entity.ProfileEntity;
import com.youtube.entity.VerificationAttemptEntity;
import com.youtube.enums.EmailCodeTypeEnum;
import com.youtube.enums.ProfileStatusEnum;
import com.youtube.exception.AppBadException;
import com.youtube.exception.ItemNotFoundException;
import com.youtube.repository.EmailHistoryRepository;
import com.youtube.repository.ProfileRepository;
import com.youtube.util.SpringSecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailSenderService senderService;
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;
    @Autowired
    private VerificationAttemptService attemptService;
    @Autowired
    private AttachService attachService;

    public String updatePassword(ProfilePasswordUpdateDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findById(SpringSecurityUtil.getCurrentProfileId());
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("User not found");
        }
        ProfileEntity profile = optional.get();
        if (!passwordEncoder.matches(dto.getOldPassword(), profile.getPassword())) {
            throw new AppBadException("Current Password is wrong!");
        }
        profile.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        profileRepository.save(profile);

        return "Successfully changed";
    }

    public String updateEmail(ProfileEmailUpdateDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            if (!optional.get().getId().equals(SpringSecurityUtil.getCurrentProfileId())) {
                throw new AppBadException("This email is taken by other user");
            } else {
                throw new AppBadException("This is your current email");
            }
        }
        senderService.verificationCode(dto.getEmail(), EmailCodeTypeEnum.UPDATE_EMAIL);
        return "Code sent to your email. Please enter the code";
    }

    @Transactional
    public String verifyAndUpdateEmail(ProfileEmailVerificationDTO dto) {
        Integer currentUserId = SpringSecurityUtil.getCurrentProfileId();
        LocalDateTime now = LocalDateTime.now();

        VerificationAttemptEntity attempt = attemptService.getByEmail(dto.getEmail());
        if (attempt == null) {
            attempt = attemptService.incrementAttempt(dto.getEmail(), attempt, now);
        }

        if (attempt.getAttemptCount() > 3 && attempt.getLastAttempt() != null) {
            LocalDateTime expiryTime = attempt.getLastAttempt().plusMinutes(2);
            if (expiryTime.isAfter(now)) {
                throw new AppBadException("Too many attempts. Please wait 2 minutes");
            } else {
                attemptService.resetAttempt(attempt);
            }
        }

        EmailHistoryEntity lastCode = emailHistoryRepository.findTopByToEmailOrderByCreatedDateDesc(dto.getEmail());

        if (lastCode == null) {
            throw new AppBadException("Verification code not found for this email");
        }

        if (lastCode.getCreatedDate().plusMinutes(2).isBefore(now)) {
            throw new AppBadException("Code is expired. Please click resend to get a new code");
        }

        if (!lastCode.getCode().equals(dto.getCode())) {
            attempt = attemptService.incrementAttempt(dto.getEmail(), attempt, now);

            int remaining = 3 - attempt.getAttemptCount();
            throw new AppBadException("Wrong code: " + (remaining > 0 ? remaining + " attempts left" : "Please try 2 minutes later"));
        }

        ProfileEntity profile = profileRepository.findById(currentUserId)
                .orElseThrow(() -> new ItemNotFoundException("User not found"));

        if (profileRepository.existsByEmail(dto.getEmail())) {
            throw new AppBadException("This email is already in use by another account");
        }

        profile.setEmail(dto.getEmail());
        profileRepository.save(profile);

        attemptService.delete(attempt);

        return "Successfully changed";
    }

    public String updateDetail(ProfileDetailUpdateDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findById(SpringSecurityUtil.getCurrentProfileId());
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("User not found");
        }
        ProfileEntity profile = optional.get();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());

        profileRepository.save(profile);

        return "Successfully updated";
    }

    @Transactional
    public String updatePhoto(ProfilePhotoUpdateDTO dto) {
        ProfileEntity profile = profileRepository.findById(SpringSecurityUtil.getCurrentProfileId())
                .orElseThrow(() -> new ItemNotFoundException("Profile not found"));

        String oldAttachId = profile.getPhotoId();
        if (dto.getAttachId().equals(oldAttachId)) {
            throw new AppBadException("Photo is already in profile");
        }

        AttachEntity attach = attachService.findById(dto.getAttachId());
        if (attach == null) {
            throw new ItemNotFoundException("Attach not found");
        }
        profile.setPhotoId(dto.getAttachId());
        profileRepository.save(profile);

        if (oldAttachId != null) {
            attachService.delete(oldAttachId);
        }

        return "Successfully changed";

    }

    public ProfileDetailDTO getProfile(Integer profileId) {
        ProfileEntity profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ItemNotFoundException("User not found"));

        ProfileDetailDTO response = new ProfileDetailDTO();
        response.setId(profileId);
        response.setName(profile.getName());
        response.setSurname(profile.getSurname());
        response.setEmail(profile.getEmail());

        if (profile.getPhotoId() != null) {
            response.setMainPhoto(new AttachShortInfoDTO(
                    profile.getPhotoId(),
                    attachService.openURL(profile.getPhotoId())
            ));
        }

        return response;
    }

    public String create(ProfileDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if(optional.isPresent()){
            throw new AppBadException("Email already exists");
        }
        ProfileEntity profile = new ProfileEntity();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setEmail(dto.getEmail());
        profile.setPassword(passwordEncoder.encode(dto.getPassword()));
        profile.setRole(dto.getRole());
        profile.setStatus(ProfileStatusEnum.ACTIVE);

        profileRepository.save(profile);

        return "Successfully created";
    }
}
