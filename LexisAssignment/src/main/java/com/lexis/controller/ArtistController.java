
package com.lexis.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lexis.model.Artist;
import com.lexis.service.ArtistService;

@RestController
@RequestMapping("/artists")
public class ArtistController {

	@Autowired
	ArtistService artistService;

	@GetMapping()
	public ResponseEntity<Page<Artist>> getAllArtists(Pageable pageable) {
		return new ResponseEntity<>(artistService.getAllArtists(pageable), HttpStatus.OK);
	}

	@GetMapping("/{name}")
	public ResponseEntity<Artist> getArtistByName(@PathVariable String name) {
		return new ResponseEntity<>(artistService.getArtistByName(name), HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<Object> saveArtist(@Valid @RequestBody final Artist artist) {

		Artist savedArtist = artistService.saveArtist(artist);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
				.buildAndExpand(savedArtist.getName()).toUri();

		return ResponseEntity.created(location).build();
		// return new ResponseEntity<>(artistService.saveArtist(artist),
		// HttpStatus.CREATED);
	}

	@PutMapping("/{artistId}")
	public ResponseEntity<Artist> updateArtist(@PathVariable Long artistId, @Valid @RequestBody Artist artistRequest) {
		return new ResponseEntity<>(artistService.updateArtist(artistId, artistRequest), HttpStatus.OK);
	}

	@DeleteMapping("/{artistId}")
	public void deleteArtistById(@PathVariable Long id) {
		artistService.deleteById(id);
	}

	@GetMapping("/search1/{searchString}")
	public ResponseEntity<List<Artist>> getArtistByNameContaining(
			@PathVariable("searchString") final String searchString) {
		List<Artist> artistList = artistService.getArtistByNameContaining(searchString);
		return new ResponseEntity<>(artistList, HttpStatus.OK);
	}

	@GetMapping("/search2/{searchString}")
	public ResponseEntity<List<Artist>> getArtistByNameLike(@PathVariable("searchString") final String searchString) {
		List<Artist> artistList = artistService.getArtistByNameLike(searchString);
		return new ResponseEntity<>(artistList, HttpStatus.OK);
	}

}
