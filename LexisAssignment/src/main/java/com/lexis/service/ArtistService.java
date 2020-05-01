package com.lexis.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lexis.model.Artist;

public interface ArtistService {

	Page<Artist> getAllArtists(Pageable pageable);

	Artist saveArtist(Artist artist);

	Artist updateArtist(Long artistId, Artist artistRequest);

	List<Artist> getArtistByNameContaining(String searchString);

	List<Artist> getArtistByNameLike(String searchString);

	Artist getArtistByName(String name);

	void deleteById(Long id);
}