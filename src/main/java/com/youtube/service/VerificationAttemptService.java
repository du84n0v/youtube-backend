package com.youtube.service;

import com.youtube.entity.VerificationAttemptEntity;
import com.youtube.repository.VerificationAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class VerificationAttemptService {

    @Autowired
    private VerificationAttemptRepository attemptRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public VerificationAttemptEntity incrementAttempt(String email, VerificationAttemptEntity attempt) {
        if (attempt == null) {
            attempt = new VerificationAttemptEntity();
            attempt.setEmail(email);
            attempt.setAttemptCount(0);
            return attemptRepository.save(attempt);
        }
        attempt.setAttemptCount(attempt.getAttemptCount() + 1);
        attempt.setLastAttempt(LocalDateTime.now());
        return attemptRepository.save(attempt);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void resetAttempt(VerificationAttemptEntity attempt) {
        if (attempt != null) {
            attempt.setAttemptCount(0);
            attemptRepository.save(attempt);
        }
    }

    public VerificationAttemptEntity getByEmail(String email) {
        return attemptRepository.findByEmail(email);
    }

    public VerificationAttemptEntity create(VerificationAttemptEntity attempt) {
        return attemptRepository.save(attempt);
    }

    public void delete(VerificationAttemptEntity attempt) {
        attemptRepository.delete(attempt);
    }
}