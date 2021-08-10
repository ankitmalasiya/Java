package com.lexis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lexis.model.Artist;

public interface ArtistService {

    Page<Artist> getAllArtists(Pageable pageable);

    Artist saveArtist(Artist artist);

    Artist updateArtist(Long artistId, Artist artistRequest);

    void deleteById(Long id);

    Optional<Artist> getArtistByName(String name);

    List<Artist> getArtistByNameLike(String searchString);

    List<Artist> getArtistByNameContaining(String searchString);

}