package com.lexis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lexis.model.Artist;

@Repository
public interface ArtistRepository extends PagingAndSortingRepository<Artist, Long> {

    Optional<Artist> findByName(String name);

    List<Artist> findByNameContaining(String value);

    @Query("SELECT a FROM Artist a WHERE a.name LIKE %:value%")
    List<Artist> findByNameLike(@Param("value") String value);

}
