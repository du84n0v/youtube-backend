package com.youtube.repository;

import com.youtube.entity.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    @Query("from CategoryEntity order by createdDate desc")
    List<CategoryEntity> getAllByOrderByCreatedDateDesc();
}
