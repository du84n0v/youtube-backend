package com.youtube.repository;

import com.youtube.entity.ChannelEntity;
import com.youtube.entity.ProfileEntity;
import com.youtube.entity.ReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends CrudRepository<ReportEntity,Integer> {
    @Query("from ReportEntity where id=:id")
    Optional<ReportEntity> findReportById(Integer id);

    @Query("from ReportEntity order by createdDate desc")
    Page<ReportEntity> pagination(Pageable pageable);


    @Query("from ReportEntity where profileId=:id")
    List<ReportEntity> getReportEntitiesByProfileId(@Param("id") Integer id);

}
