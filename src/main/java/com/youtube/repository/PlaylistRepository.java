package com.youtube.repository;

import com.youtube.entity.PlaylistEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends CrudRepository<PlaylistEntity, Integer>, PagingAndSortingRepository<PlaylistEntity, Integer> {
    @Query("FROM PlaylistEntity WHERE channel.owner.id =:id ORDER BY orderNumber DESC ")
    Optional<List<PlaylistEntity>> findByUserId(Integer id);

    @Query("FROM PlaylistEntity WHERE channel.id =:channelKey ORDER BY orderNumber DESC ")
    Optional<List<PlaylistEntity>> findAllByChannelKey(String channelKey);
}
