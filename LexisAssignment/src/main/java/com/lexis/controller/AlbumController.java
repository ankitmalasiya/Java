package com.lexis.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.lexis.model.Artist;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lexis.exception.ResourceNotFoundException;
import com.lexis.model.Album;
import com.lexis.repository.AlbumRepository;
import com.lexis.repository.ArtistRepository;

@RestController
@RequestMapping("/artists/{artistId}/albums")
@AllArgsConstructor
public class AlbumController {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    @GetMapping
    public Page<Album> getAllAlbumsByArtistId(@PathVariable(value = "artistId") Long artistId, Pageable pageable) {
        return albumRepository.findByArtistId(artistId, pageable);
    }

    @GetMapping("/{albumId}")
    public Album getAlbumsById(@PathVariable Long artistId, @PathVariable Long albumId) {
        List<Album> albums = albumRepository.findByArtistId(artistId);
        return albums.stream().filter(a -> a.getId().equals(albumId)).findFirst().get();
    }

//    @GetMapping("/{genre}")
//    public Album getAlbumByGenre(@PathVariable Long artistId, @PathVariable String genre) {
//        if (!artistRepository.existsById(artistId)) {
//            throw new ResourceNotFoundException("ArtistId " + artistId + " not found");
//        }
//        return albumRepository.findByArtistId(artistId).stream().filter(a -> a.getGenres().contains(genre)).findFirst().get();
//    }

    @PostMapping
    public Album createAlbumForArtist(@PathVariable Long artistId, @Valid @RequestBody Album album) {
        return artistRepository.findById(artistId).map(artist -> {
            album.setArtist(artist);
            return albumRepository.save(album);
        }).orElseThrow(() -> new ResourceNotFoundException("ArtistId " + artistId + " not found"));
    }

    @PutMapping("/{albumId}")
    public Album updateAlbum(@PathVariable Long artistId, @PathVariable Long albumId, @Valid @RequestBody Album albumRequest) {
        if (!artistRepository.existsById(artistId)) {
            throw new ResourceNotFoundException("ArtistId " + artistId + " not found");
        }

        return albumRepository.findById(albumId).map(album -> {
            album.setTitle(albumRequest.getTitle());
            return albumRepository.save(album);
        }).orElseThrow(() -> new ResourceNotFoundException("AlbumId " + albumId + "not found"));
    }

    @PutMapping("/{albumId}")
    public ResponseEntity<Artist> deleteAlbum(@PathVariable Long artistId, @PathVariable Long albumId) {
        if (!artistRepository.existsById(artistId)) {
            throw new ResourceNotFoundException("ArtistId " + artistId + " not found");
        }
        Optional<Album> optionalAlbum = albumRepository.findById(albumId);

        if (optionalAlbum.isPresent()) {
            albumRepository.deleteById(albumId);
        } else throw new ResourceNotFoundException("AlbumId " + albumId + "not found");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}