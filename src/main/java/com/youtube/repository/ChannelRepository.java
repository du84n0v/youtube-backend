package com.youtube.repository;


import com.youtube.entity.ChannelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends CrudRepository<ChannelEntity,String> {

    @Query("select ch from ChannelEntity ch where ch.name=: name and ch.status='ACTIVE'")
    Optional<ChannelEntity> findByName(String name);

    @Query("from ChannelEntity where status ='ACTIVE' order by createdDate desc")
    Page<ChannelEntity> pagination(Pageable pageable);

    @Query("select ch from ChannelEntity ch where ch.id=: id and ch.status='ACTIVE'")
    Optional<ChannelEntity> findByIdAndStatusIsActive(String id);

    @Query("from ChannelEntity where profileId=:id and status='ACTIVE'")
    List<ChannelEntity> getChannelEntitiesByProfileId(String id);
}