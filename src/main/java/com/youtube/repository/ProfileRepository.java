package com.youtube.repository;

import com.youtube.entity.ProfileEntity;
import com.youtube.enums.ProfileStatusEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByEmailAndStatus(String username, ProfileStatusEnum profileStatusEnum);

    Optional<ProfileEntity> findByEmail(String email);
}
