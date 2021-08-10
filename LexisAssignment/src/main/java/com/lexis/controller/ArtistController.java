
package com.lexis.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lexis.model.Artist;
import com.lexis.service.ArtistService;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    ArtistService service;

    public static final String ARTISTS = "artists";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String TOTAL_ITEMS = "totalItems";
    public static final String TOTAL_PAGES = "totalPages";

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllArtists(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {

        try {
            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
            Page<Artist> artistPage = service.getAllArtists(paging);
            return getMapResponseEntity(artistPage);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<Object> saveArtist(@Valid @RequestBody final Artist artist) {

        Artist savedArtist = service.saveArtist(artist);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(savedArtist.getName()).toUri();

        return ResponseEntity.created(location).build();
        // return new ResponseEntity<>(artistService.saveArtist(artist),
        // HttpStatus.CREATED);
    }

    @PutMapping("/{artistId}")
    public ResponseEntity<Artist> updateArtist(@PathVariable Long artistId, @Valid @RequestBody Artist artistRequest) {
        return new ResponseEntity<>(service.updateArtist(artistId, artistRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{artistId}")
    public ResponseEntity<Artist> deleteArtistById(@PathVariable Long id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<Page<Artist>> getAllArtists(Pageable pageable) {
        return new ResponseEntity<>(service.getAllArtists(pageable), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Artist> getArtistByName(@PathVariable String name) {
        Optional<Artist> artistByName = service.getArtistByName(name);
        return artistByName.map(artist -> new ResponseEntity<>(artist, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nameContaining/{searchString}")
    public ResponseEntity<Object> getArtistByNameContaining(@PathVariable("searchString") final String searchString) {
        List<Artist> artistList = service.getArtistByNameContaining(searchString);
        if (artistList.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(artistList, HttpStatus.OK);
    }

    @GetMapping("/nameLike/{searchString}")
    public ResponseEntity<List<Artist>> getArtistByNameLike(@PathVariable("searchString") final String searchString) {
        List<Artist> artistList = service.getArtistByNameLike(searchString);
        if (artistList.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(artistList, HttpStatus.OK);
    }


    private ResponseEntity<Map<String, Object>> getMapResponseEntity(Page<Artist> artistPage) {
        Map<String, Object> response = new HashMap<>();
        response.put(ARTISTS, artistPage.getContent());
        response.put(CURRENT_PAGE, artistPage.getNumber());
        response.put(TOTAL_ITEMS, artistPage.getTotalElements());
        response.put(TOTAL_PAGES, artistPage.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}