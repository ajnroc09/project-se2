package edu.hanu.fit.se2.project.music_web.repository;

import edu.hanu.fit.se2.project.music_web.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist,Long> {
}
