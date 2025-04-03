package edu.hanu.fit.se2.project.music_web.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "albums")
public class Album {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long albumID;

	@Column(nullable = false, length = 255)
	private String albumName;

	@Column
	private Date releaseDate;

	@Column(length = 255)
	private String coverImage;


	//----------------
	//relationship
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="album_artist",
			joinColumns = @JoinColumn(name = "album_id"),
			inverseJoinColumns = @JoinColumn(name = "artist_id"))
	private List<Artist> artistsOfAlbum;

	@OneToMany(mappedBy = "album", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Song> songsOfAlbum;

	// getters & setters


	public Long getAlbumID() {
		return albumID;
	}

	public void setAlbumID(Long albumID) {
		this.albumID = albumID;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public List<Artist> getArtistsOfAlbum() {
		return artistsOfAlbum;
	}

	public void setArtistsOfAlbum(List<Artist> artistsOfAlbum) {
		this.artistsOfAlbum = artistsOfAlbum;
	}

	public List<Song> getSongsOfAlbum() {
		return songsOfAlbum;
	}

	public void setSongsOfAlbum(List<Song> songsOfAlbum) {
		this.songsOfAlbum = songsOfAlbum;
	}
}
