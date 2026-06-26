package com.youtube.repository;

import com.youtube.entity.PlaylistEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends CrudRepository<PlaylistEntity, Integer>, PagingAndSortingRepository<PlaylistEntity, Integer> {
    @Query("from PlaylistEntity where channel.owner.id =:id order by orderNumber desc")
    Optional<List<PlaylistEntity>> findByUserId(Integer id);
}
