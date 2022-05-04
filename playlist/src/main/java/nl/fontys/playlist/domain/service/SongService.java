package nl.fontys.playlist.domain.service;

import nl.fontys.playlist.database.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongService {

    private SongRepository repository;

    @Autowired
    public SongService(SongRepository repository) {
        this.repository = repository;
    }
}
