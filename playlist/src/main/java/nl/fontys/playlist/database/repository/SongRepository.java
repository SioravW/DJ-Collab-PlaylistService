package nl.fontys.playlist.database.repository;

import nl.fontys.playlist.domain.models.Song;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SongRepository  extends MongoRepository<Song, Long> {
}
