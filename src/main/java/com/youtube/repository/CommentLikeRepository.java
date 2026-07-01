package com.youtube.repository;

import com.youtube.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, Integer> {
    @Query("FROM CommentLikeEntity " +
            "WHERE commentId =:commentId " +
            "AND profileId =:profileId")
    Optional<CommentLikeEntity> findByCommentIdAndProfileId(Integer profileId, Integer commentId);

    Optional<List<CommentLikeEntity>> findByProfileId(Integer profileId);
}
