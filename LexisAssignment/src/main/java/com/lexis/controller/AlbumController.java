package com.lexis.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lexis.exception.ResourceNotFoundException;
import com.lexis.model.Album;
import com.lexis.repository.AlbumRepository;
import com.lexis.repository.ArtistRepository;

@RestController
public class AlbumController {

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private ArtistRepository artistRepository;

	@GetMapping("/artists/{artistId}/albums/{albumId}")
	public Album getAlbumsById(@PathVariable Long artistId, @PathVariable Long albumId) {
		List<Album> albums = albumRepository.findByArtistId(artistId);
		return albums.stream().filter(a -> a.getId().equals(albumId)).findFirst().get();
	}

	@GetMapping("/artists/{artistId}/albums")
	public Page<Album> getAllAlbumsByArtistId(@PathVariable(value = "artistId") Long artistId, Pageable pageable) {
		return albumRepository.findByArtistId(artistId, pageable);
	}

	@PostMapping("/artists/{artistId}/albums")
	public Album createAlbumForArtist(@PathVariable(value = "artistId") Long artistId,
			@Valid @RequestBody Album album) {
		return artistRepository.findById(artistId).map(artist -> {
			album.setArtist(artist);
			return albumRepository.save(album);
		}).orElseThrow(() -> new ResourceNotFoundException("ArtistId " + artistId + " not found"));
	}

	@PutMapping("/artists/{artistId}/albums/{albumId}")
	public Album updateAlbum(@PathVariable(value = "artistId") Long artistId,
			@PathVariable(value = "albumId") Long albumId, @Valid @RequestBody Album albumRequest) {
		if (!artistRepository.existsById(artistId)) {
			throw new ResourceNotFoundException("ArtistId " + artistId + " not found");
		}

		return albumRepository.findById(albumId).map(album -> {
			album.setTitle(albumRequest.getTitle());
			return albumRepository.save(album);
		}).orElseThrow(() -> new ResourceNotFoundException("AlbumId " + albumId + "not found"));
	}

//	@GetMapping("/artists/{artistId}/albums/{genre}")
	public Album getAlbumByGenre(@PathVariable(value = "artistId") Long artistId,
			@PathVariable(value = "genre") String genre) {

		if (!artistRepository.existsById(artistId)) {
			throw new ResourceNotFoundException("ArtistId " + artistId + " not found");
		}
		return albumRepository.findByArtistId(artistId).stream().filter(a -> a.getGenres().contains(genre)).findFirst()
				.get();
	}
}
