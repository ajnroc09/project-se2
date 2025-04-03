package edu.hanu.fit.se2.project.music_web.repository;

import edu.hanu.fit.se2.project.music_web.controller.SongController;
import edu.hanu.fit.se2.project.music_web.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song,Long> {
}
