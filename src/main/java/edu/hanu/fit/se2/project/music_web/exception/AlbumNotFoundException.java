package edu.hanu.fit.se2.project.music_web.exception;

public class AlbumNotFoundException extends Exception {
	private String msg;

	public AlbumNotFoundException(String msg) {
		super(msg);
		this.msg = msg;
	}
}
