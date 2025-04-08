package edu.hanu.fit.se2.project.music_web.controller;

import edu.hanu.fit.se2.project.music_web.exception.AlbumNotFoundException;
import edu.hanu.fit.se2.project.music_web.exception.SongNotFoundException;
import edu.hanu.fit.se2.project.music_web.model.Album;
import edu.hanu.fit.se2.project.music_web.model.Artist;
import edu.hanu.fit.se2.project.music_web.model.Genre;
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

//1.	Create Song (Upload)
//2.	Get All Songs
//6.	Update Song
//7.	Delete Song

@Controller
@RequestMapping("/song")
public class SongController {
	@Autowired
	SongRepository songRepository;
	@Autowired
	GenreRepository genreRepository;
	@Autowired
	ArtistRepository artistRepository;
	@Autowired
	AlbumRepository albumRepository;

	@GetMapping(value = "/list")
	public String getAllSongs(Model model) {
		List<Song> songs = songRepository.findAll();
		model.addAttribute("songs", songs);
		return "songList";
	}

	@GetMapping(value = "/update/{id}")
	public String updateSong(@PathVariable("id") Long id, Model model)
			throws SongNotFoundException {
		Song song = songRepository.findById(id)
				.orElseThrow(() -> new SongNotFoundException("Song not found"));

		List<Artist> allArtists = artistRepository.findAll();
		List<Genre> allGenres = genreRepository.findAll();
		List<Album> allAlbums = albumRepository.findAll();

		model.addAttribute("song", song);
		model.addAttribute("artists", allArtists);
		model.addAttribute("genres", allGenres);
		model.addAttribute("albums", allAlbums);

		return "songUpdate";
	}


	@PostMapping(value = "/save")
	public String saveUpdate(
			@ModelAttribute Song song,
			@RequestParam("artistsOfSong") List<Long> artistIds,
			@RequestParam("genresOfSong") List<Long> genreIds,
			@RequestParam("album") Long albumId
	) throws AlbumNotFoundException {
		List<Artist> artists = artistRepository.findAllById(artistIds);
		List<Genre> genres = genreRepository.findAllById(genreIds);
		Album album = albumRepository.findById(song.getAlbum().getAlbumID())
				.orElseThrow(() -> new AlbumNotFoundException("Album not found"));
		song.setArtistsOfSong(artists);
		song.setGenresOfSong(genres);
		song.setAlbum(album);

		songRepository.save(song);
		return "redirect:/song/list";
	}

	@GetMapping(value = "/add")
	public String addSong(Model model) {
		Song song = new Song();
		List<Artist> artists = artistRepository.findAll();
		List<Album> albumList = albumRepository.findAll();
		List<Genre> genres = genreRepository.findAll();

		model.addAttribute("song", song);
		model.addAttribute("artists", artists);
		model.addAttribute("albumList", albumList);
		model.addAttribute("genres", genres);
		return "songAdd";
	}

	@PostMapping(value = "/add")
	public String addSong(Song song) {
		songRepository.save(song);
		return "redirect:/song/list";
	}


	@GetMapping(value = "/delete/{id}")
	public String deleteSong(@PathVariable(value = "id") Long id) throws SongNotFoundException {
		Song song = songRepository.findById(id)
				.orElseThrow(() -> new SongNotFoundException("Song not found"));
		songRepository.delete(song);
		return "redirect:/song/list";
	}

}
