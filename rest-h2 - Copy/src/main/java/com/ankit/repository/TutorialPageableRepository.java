package com.ankit.repository;

import com.ankit.model.Tutorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TutorialPageableRepository extends PagingAndSortingRepository<Tutorial, Long> {

    Page<Tutorial> findAll(Pageable pageable);
    Page<Tutorial> findByPublished(boolean published, Pageable pageable);
    Page<Tutorial> findByTitleContaining(String title, Pageable pageable);
}