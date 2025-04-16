package edu.hanu.fit.se2.project.music_web.controller;

import edu.hanu.fit.se2.project.music_web.exception.AlbumNotFoundException;
import edu.hanu.fit.se2.project.music_web.model.Album;
import edu.hanu.fit.se2.project.music_web.model.Artist;
import edu.hanu.fit.se2.project.music_web.model.Song;
import edu.hanu.fit.se2.project.music_web.repository.AlbumRepository;
import edu.hanu.fit.se2.project.music_web.repository.ArtistRepository;
import edu.hanu.fit.se2.project.music_web.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	ArtistRepository artistRepository;
	@Autowired
	AlbumRepository albumRepository;

	@GetMapping(value = "/list")
	public String getAllAlbums(Model model) {
		List<Album> albums = albumRepository.findAll();
		List<Song> songs = songRepository.findAll();
		List<Artist> artists = artistRepository.findAll();
		model.addAttribute("albums", albums);
		model.addAttribute("songs", songs);
		model.addAttribute("artists", artists);
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
	//---------------------------------
	@PostMapping("/update")
	public String UpdatedAlbum(Album album) {
		// Convert songIds and artistIds back to full objects
		List<Song> selectedSongs = songRepository.findAllById(album.getSongIds());
		List<Artist> selectedArtists = artistRepository.findAllById(album.getArtistIds());

		for (Song song : selectedSongs) {
			song.setAlbum(album); // important for OneToMany
		}

		album.setSongsOfAlbum(selectedSongs);
		album.setArtistsOfAlbum(selectedArtists);

		albumRepository.save(album);
		return "redirect:/album/list";
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

//	@PostMapping(value = "/add")
//	public String addAlbum(Album album) {
//		System.out.println(">>> Album name: " + album.getAlbumName());
//		albumRepository.save(album);
//		return "redirect:/album/list";
//	}

	@PostMapping("/add")
	public String addAlbum(Album album) {
		List<Song> selectedSongs = songRepository.findAllById(album.getSongIds());

		// lấy tất cả artist từ những bài hát
		List<Artist> uniqueArtists = selectedSongs.stream()
				.flatMap(song -> song.getArtistsOfSong().stream())
				.collect(Collectors.toList());

		// cập nhật album cho mỗi bài hát
		for (Song song : selectedSongs) {
			song.setAlbum(album);
		}

		album.setSongsOfAlbum(selectedSongs);
		album.setArtistsOfAlbum(new ArrayList<>(uniqueArtists));

		albumRepository.save(album);
		return "redirect:/album/list";
	}

	//------------

	@GetMapping(value = "/delete/{id}")
	public String deleteAlbum(@PathVariable(value = "id") Long id) throws AlbumNotFoundException {
		Album album = albumRepository.findById(id)
				.orElseThrow(()->new AlbumNotFoundException("Album not found"));
		albumRepository.delete(album);
		return "redirect:/album/list";
	}
	//
}

