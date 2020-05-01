package com.lexis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lexis.model.Artist;
import com.lexis.repository.ArtistRepository;
import com.lexis.service.ArtistServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class ArtistServiceTest {

	@Mock
	private ArtistRepository artistRepository;

	@InjectMocks
	private ArtistServiceImpl artistService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetArtistByName() {
		Artist artist = new Artist(1L, "Artist");
		when(artistRepository.findByName("Artist")).thenReturn(artist);
		Artist result = artistService.getArtistByName("Artist");
		assertEquals("Artist", result.getName());
	}

	@Test
	public void testGetAllArtists() {
		List<Artist> artistList = new ArrayList<Artist>();
		artistList.add(new Artist(1L, "Todo Sample 1"));
		artistList.add(new Artist(2L, "Todo Sample 2"));
		artistList.add(new Artist(3L, "Todo Sample 3"));
		when(artistRepository.findAll()).thenReturn(artistList);

		// List<Artist> result = artistService.getAllArtists();
		// assertEquals(3, result.size());
	}

	@Test
	public void saveArtist() {
		Artist artist = new Artist(1L, "Artist 8");
		when(artistRepository.save(artist)).thenReturn(artist);
		Artist result = artistService.saveArtist(artist);

		verify(artistRepository).save(any(Artist.class));
		assertEquals("1", result.getId().toString());
	}

	@Test
	public void updateArtist() {
		Artist artistUpdate = new Artist(null, "Artist 8");
		Artist artist1 = new Artist(1L, "Artist");
		Optional<Artist> artist2 = Optional.of(artist1);
		when(artistRepository.findById(1L)).thenReturn(artist2);
		when(artistRepository.save(artistUpdate)).thenReturn(artist1);

		Artist result = artistService.updateArtist(1L, artistUpdate);
		assertNotNull(result.getId());
	}

}