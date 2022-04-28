package nl.fontys.playlist.Domain.Service;


import nl.fontys.playlist.Database.Repository.IPlaylistRepository;
import nl.fontys.playlist.Domain.Models.Playlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    public List<Playlist> playlists;
    private IPlaylistRepository repository;

    @Autowired
    public PlaylistService(IPlaylistRepository repository) {
        this.repository = repository;
    }

    public List<Playlist> getAllPlaylists()
    {
        return repository.findAll();
    }
}
