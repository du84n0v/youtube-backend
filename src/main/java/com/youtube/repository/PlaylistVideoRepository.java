package com.youtube.repository;

import com.youtube.entity.PlaylistVideoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistVideoRepository extends CrudRepository<PlaylistVideoEntity, Integer> {
    @Query("FROM PlaylistVideoEntity WHERE playlistId=:playlistId AND videoId=:videoId AND visible IS TRUE")
    Optional<PlaylistVideoEntity> findByPlaylistIdAndVideoId(Integer playlistId, String videoId);

    Optional<List<PlaylistVideoEntity>> findByPlaylistId(Integer playlistId);
}
