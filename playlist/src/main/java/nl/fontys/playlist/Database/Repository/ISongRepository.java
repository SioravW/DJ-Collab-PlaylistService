package nl.fontys.playlist.Database.Repository;

import nl.fontys.playlist.Domain.Models.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISongRepository extends JpaRepository<Song, Long> {
}
