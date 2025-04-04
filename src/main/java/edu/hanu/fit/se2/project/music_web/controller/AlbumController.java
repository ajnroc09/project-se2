package edu.hanu.fit.se2.project.music_web.controller;

import edu.hanu.fit.se2.project.music_web.exception.AlbumNotFoundException;
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

//1.	Create Album
//2.	Get All Albums
//5.	Update Album
//6.	Delete Album

@Controller
@RequestMapping(value ="/album")
public class AlbumController {
	@Autowired
	SongRepository songRepository;
	@Autowired
	GenreRepository genreRepository;
	@Autowired
	ArtistRepository artistRepository;
	@Autowired
	AlbumRepository albumRepository;

	@GetMapping(value = "/list")
	public String getAllAlbums(Model model) {
		List<Album> albums = albumRepository.findAll();
		model.addAttribute("albums", albums);
		return "albumList";
	}

	@GetMapping(value = "/update/{id}")
	public String updateAlbum(
			@PathVariable(value = "id") Long id, Model model) throws AlbumNotFoundException {
		Album album = albumRepository.findById(id)
				.orElseThrow(()->new AlbumNotFoundException("Album not found"));
		List<Song> songs = new ArrayList<>(album.getSongsOfAlbum());
		List<Artist> artists = new ArrayList<>(album.getArtistsOfAlbum());

		album.setSongsOfAlbum(songs);
		album.setArtistsOfAlbum(artists);

		model.addAttribute("songs", songs);
		model.addAttribute("artists", artists);
		model.addAttribute("album",album);
		return "albumUpdate";
	}

	@GetMapping(value = "/add")
	public String addAlbum(Model model) {
		Album album = new Album();
		List<Song> songs = songRepository.findAll();
		List<Artist> artists =artistRepository.findAll();

		model.addAttribute("songs", songs);
		model.addAttribute("artists", artists);
		model.addAttribute("album",album);
		return "albumAdd";
	}

	@PostMapping(value = "/add")
	public String addAlbum(Album album) {
		System.out.println(">>> Album name: " + album.getAlbumName());
		albumRepository.save(album);
		return "redirect:/album/list";
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteAlbum(@PathVariable(value = "id") Long id) throws AlbumNotFoundException {
		Album album = albumRepository.findById(id)
				.orElseThrow(()->new AlbumNotFoundException("Album not found"));
		albumRepository.delete(album);
		return "redirect:/album/list";
	}
	//
}

