package com.lexis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lexis.model.Artist;
import com.lexis.repository.ArtistRepository;

@Service("artistService")
public class ArtistServiceImpl implements ArtistService {

	@Autowired
	ArtistRepository artistRepository;

	@Override
	public Artist getArtistByName(String name) {
		return artistRepository.findByName(name);
	}

	public Page<Artist> getAllArtists(Pageable pageable) {
		return artistRepository.findAll(pageable);
	}

	@Override
	public Artist saveArtist(Artist artist) {
		return artistRepository.save(artist);
	}

	public Artist updateArtist(Long artistId, Artist artistRequest) {
		if (artistRepository.findById(artistId).isPresent()) {
			artistRequest.setId(artistId);
			return artistRepository.save(artistRequest);
		}
		return artistRequest;
	}

	@Override
	public void deleteById(Long artistId) {
		if (artistRepository.findById(artistId).isPresent()) {
			artistRepository.deleteById(artistId);
		}
	}

	public List<Artist> getArtistByNameContaining(String searchString) {
		return artistRepository.findByNameContaining(searchString);
	}

	public List<Artist> getArtistByNameLike(String searchString) {
		return artistRepository.findByNameLike(searchString);
	}

}