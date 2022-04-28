package nl.fontys.playlist.Domain.Service;

import nl.fontys.playlist.Database.Repository.IPlaylistRepository;
import nl.fontys.playlist.Database.Repository.ISongRepository;
import nl.fontys.playlist.Domain.Models.Playlist;
import nl.fontys.playlist.Domain.Models.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    public List<Song> songs;
    private ISongRepository repository;

    @Autowired
    public SongService(ISongRepository repository) {
        this.repository = repository;
    }
}
