package com.lexis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lexis.model.Artist;
import com.lexis.repository.ArtistRepository;

@Service("artistService")
public class ArtistServiceImpl implements ArtistService {

    @Autowired
    ArtistRepository repository;

    public Page<Artist> getAllArtists(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Artist saveArtist(Artist artist) {
        return repository.save(artist);
    }

    public Artist updateArtist(Long artistId, Artist artistRequest) {
        if (repository.findById(artistId).isPresent()) {
            artistRequest.setId(artistId);
            return repository.save(artistRequest);
        }
        return artistRequest;
    }

    @Override
    public void deleteById(Long artistId) {
        if (repository.findById(artistId).isPresent()) {
            repository.deleteById(artistId);
        }
    }

    @Override
    public Optional<Artist> getArtistByName(String name) {
        return repository.findByName(name);
    }

    public List<Artist> getArtistByNameContaining(String searchString) {
        return repository.findByNameContaining(searchString);
    }

    public List<Artist> getArtistByNameLike(String searchString) {
        return repository.findByNameLike(searchString);
    }
}