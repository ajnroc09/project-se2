package edu.hanu.fit.se2.project.music_web.repository;

import edu.hanu.fit.se2.project.music_web.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre,Long> {
}
