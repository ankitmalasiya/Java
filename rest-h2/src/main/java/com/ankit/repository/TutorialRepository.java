package com.ankit.repository;

import com.ankit.model.Tutorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TutorialRepository extends PagingAndSortingRepository<Tutorial, Long> {

    List<Tutorial> findByPublished(boolean published);
    List<Tutorial> findByTitleContaining(String title);

    Page<Tutorial> findAll(Pageable pageable);
    Page<Tutorial> findByPublished(boolean published, Pageable pageable);
    Page<Tutorial> findByTitleContaining(String title, Pageable pageable);

}