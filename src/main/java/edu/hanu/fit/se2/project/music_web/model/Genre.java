package edu.hanu.fit.se2.project.music_web.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "genres")
public class Genre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long genreID;

	@Column(nullable = false, length = 255)
	private String genreName;

	//----------
	//relationship
	@ManyToMany(mappedBy = "genresOfSong")
	private List<Song> songsOfGenre;

	//getters & setters

	public Long getGenreID() {
		return genreID;
	}

	public void setGenreID(Long genreID) {
		this.genreID = genreID;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public List<Song> getSongsOfGenre() {
		return songsOfGenre;
	}

	public void setSongsOfGenre(List<Song> songsOfGenre) {
		this.songsOfGenre = songsOfGenre;
	}
}
