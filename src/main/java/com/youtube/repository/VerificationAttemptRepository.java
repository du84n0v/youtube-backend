package com.youtube.repository;

import com.youtube.entity.VerificationAttemptEntity;
import org.springframework.data.repository.CrudRepository;

public interface VerificationAttemptRepository extends CrudRepository<VerificationAttemptEntity, Integer> {

    VerificationAttemptEntity findByEmail(String email);
}
