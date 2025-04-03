package edu.hanu.fit.se2.project.music_web.exception;

public class ArtistNotFoundException extends Exception {
	private String msg;

	public ArtistNotFoundException(String msg) {
		super(msg);
		this.msg = msg;
	}
}
