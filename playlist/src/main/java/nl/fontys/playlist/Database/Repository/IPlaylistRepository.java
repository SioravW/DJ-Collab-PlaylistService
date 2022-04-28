package nl.fontys.playlist.Database.Repository;

import nl.fontys.playlist.Domain.Models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlaylistRepository extends JpaRepository<Playlist, Long> {
}
