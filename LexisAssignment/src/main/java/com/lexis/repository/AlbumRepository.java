package com.lexis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lexis.model.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
	Page<Album> findByArtistId(Long artistId, Pageable pageable);

	Optional<Album> findByIdAndArtistId(Long id, Long artistId);

	List<Album> findByArtistId(Long artistId);

}
