package edu.hanu.fit.se2.project.music_web.exception;

public class SongNotFoundException extends Exception {
	private String msg;

	public SongNotFoundException(String msg) {
		super(msg);
		this.msg = msg;
	}
}
