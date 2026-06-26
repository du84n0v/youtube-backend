package com.youtube.service;

import com.youtube.dto.profile.ProfileEmailVerificationDTO;
import com.youtube.dto.profile.ProfileUpdateEmailDTO;
import com.youtube.dto.profile.ProfileUpdatePasswordDTO;
import com.youtube.entity.EmailHistoryEntity;
import com.youtube.entity.ProfileEntity;
import com.youtube.entity.VerificationAttemptEntity;
import com.youtube.enums.EmailCodeTypeEnum;
import com.youtube.enums.ProfileStatusEnum;
import com.youtube.exception.AppBadException;
import com.youtube.exception.ItemNotFoundException;
import com.youtube.repository.EmailHistoryRepository;
import com.youtube.repository.ProfileRepository;
import com.youtube.repository.VerificationAttemptRepository;
import com.youtube.util.SpringSecurityUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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

    public String updatePassword(ProfileUpdatePasswordDTO dto) {
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

    public String updateEmail(ProfileUpdateEmailDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if(optional.isPresent()){
            if(!optional.get().getId().equals(SpringSecurityUtil.getCurrentProfileId())){
                throw new AppBadException("This email is taken by other user");
            }
            else{
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

}
