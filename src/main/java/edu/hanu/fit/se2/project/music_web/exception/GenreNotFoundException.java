package edu.hanu.fit.se2.project.music_web.exception;

public class GenreNotFoundException extends Exception {
	private String msg;

	public GenreNotFoundException(String msg) {
		super(msg);
		this.msg = msg;
	}
}
