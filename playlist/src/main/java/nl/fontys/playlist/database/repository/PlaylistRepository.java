package nl.fontys.playlist.database.repository;

import nl.fontys.playlist.domain.models.Playlist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface PlaylistRepository  extends MongoRepository<Playlist, Long> {
    Optional<Playlist> findByExternalId(UUID id);
    ArrayList<Playlist> findByUserId(UUID id);
}
