package com.youtube.repository;


import com.youtube.entity.ChannelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends CrudRepository<ChannelEntity, String> {

    @Query("select ch from ChannelEntity ch where ch.name=:name and ch.status='ACTIVE'")
    Optional<ChannelEntity> findByName(@Param("name") String name);

    @Query("from ChannelEntity where status ='ACTIVE' order by createdDate desc")
    Page<ChannelEntity> pagination(Pageable pageable);

    @Query("select ch from ChannelEntity ch where ch.id=:id and ch.status='ACTIVE'")
    Optional<ChannelEntity> findByIdAndStatusIsActive(@Param("id") String id);

    @Query("from ChannelEntity where profileId=:id and status='ACTIVE'")
    List<ChannelEntity> getChannelEntitiesByProfileId(@Param("id") Integer id);

    @Query("from ChannelEntity where id=:channelId and profileId=:prfile_id")
    ChannelEntity findByIdAndProfileId(@Param("channelId") String channelId, @Param("profile_id") Integer profile_id);

    boolean existsByIdAndVisibleTrue(String channelId, Boolean visible);
}