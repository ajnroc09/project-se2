package edu.hanu.fit.se2.project.music_web.controller;

import edu.hanu.fit.se2.project.music_web.exception.ArtistNotFoundException;
import edu.hanu.fit.se2.project.music_web.model.Album;
import edu.hanu.fit.se2.project.music_web.model.Artist;
import edu.hanu.fit.se2.project.music_web.model.Song;
import edu.hanu.fit.se2.project.music_web.repository.AlbumRepository;
import edu.hanu.fit.se2.project.music_web.repository.ArtistRepository;
import edu.hanu.fit.se2.project.music_web.repository.GenreRepository;
import edu.hanu.fit.se2.project.music_web.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

//1.	Create Artist
//2.	Get All Artists
//4.	Update Artist
//5.	Delete Artist
@Controller
@RequestMapping(value ="/artist")
public class ArtistController {
	@Autowired
	SongRepository songRepository;
	@Autowired
	GenreRepository genreRepository;
	@Autowired
	ArtistRepository artistRepository;
	@Autowired
	AlbumRepository albumRepository;

	@GetMapping(value = "/list")
	public String getAllArtists(Model model) {
		List<Artist> artists = artistRepository.findAll();
		model.addAttribute("artists", artists);
		return "artistList";
	}

	@GetMapping(value = "/update/{id}")
	public String updateArtist(
			@PathVariable(value = "id") Long id, Model model) throws ArtistNotFoundException {
		Artist artist = artistRepository.findById(id)
				.orElseThrow(()->new ArtistNotFoundException("Artist not found"));
		List<Album> albums = new ArrayList<>(artist.getAlbumsOfArtist());
		List<Song> songs = new ArrayList<>(artist.getSongsOfArtist());

		artist.setAlbumsOfArtist(albums);
		artist.setSongsOfArtist(songs);

		model.addAttribute("artist",artist);
		model.addAttribute("albums",albums);
		model.addAttribute("songs",songs);
		return "artistUpdate";
	}

	@PostMapping(value = "/save")
	public String saveUpdate(Artist artist) {
		artistRepository.save(artist);
		return "redirect:/artist/list";
	}

	@GetMapping(value = "/add")
	public String addArtist(Model model) {
		Artist artist = new Artist();
		List<Song> songs = songRepository.findAll();
		List<Album> albums = albumRepository.findAll();

		model.addAttribute("songs", songs);
		model.addAttribute("albums", albums);
		model.addAttribute("artist",artist);
		return "artistAdd";
	}
	@PostMapping(value = "/add")
	public String addArtist(Artist artist) {
		artistRepository.save(artist);
		return "redirect:/artist/list";
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteArtist(@PathVariable(value = "id") Long id) throws ArtistNotFoundException {
		Artist artist = artistRepository.findById(id)
				.orElseThrow(()->new ArtistNotFoundException("Artist not found"));
		artistRepository.delete(artist);
		return "redirect:/artist/list";
	}
}

