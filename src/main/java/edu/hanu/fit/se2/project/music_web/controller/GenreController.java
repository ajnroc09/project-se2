package edu.hanu.fit.se2.project.music_web.controller;

import edu.hanu.fit.se2.project.music_web.exception.GenreNotFoundException;
import edu.hanu.fit.se2.project.music_web.model.Genre;
import edu.hanu.fit.se2.project.music_web.repository.AlbumRepository;
import edu.hanu.fit.se2.project.music_web.repository.ArtistRepository;
import edu.hanu.fit.se2.project.music_web.repository.GenreRepository;
import edu.hanu.fit.se2.project.music_web.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/genre")
public class GenreController {

	@Autowired
	GenreRepository genreRepository;
	@Autowired
	SongRepository songRepository;
	@Autowired
	ArtistRepository artistRepository;
	@Autowired
	AlbumRepository albumRepository;

	@GetMapping("/list")
	public String listGenres(Model model) {
		List<Genre> genres = genreRepository.findAll();
		model.addAttribute("genres", genres);
		return "genreList";
	}

	@GetMapping("/add")
	public String addGenre(Model model) {
		model.addAttribute("genre", new Genre());
		return "genreAdd";
	}

	@PostMapping("/add")
	public String addGenre(@ModelAttribute Genre genre) {
		genreRepository.save(genre);
		return "redirect:/genre/list";
	}

	@GetMapping("/update/{id}")
	public String updateGenre(@PathVariable Long id, Model model) throws GenreNotFoundException {
		Genre genre = genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException("Invalid genre ID: " + id));
		model.addAttribute("genre", genre);
		return "genreUpdate";
	}

	@PostMapping("/update")
	public String updateGenre(@ModelAttribute Genre genre) {
		genreRepository.save(genre);
		return "redirect:/genre/list";
	}

	@GetMapping("/delete/{id}")
	public String deleteGenre(@PathVariable Long id) throws GenreNotFoundException {
		Genre genre = genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException("Genre not found"));
		genreRepository.delete(genre);
		return "redirect:/genre/list";
	}
}
