package nl.fontys.playlist.domain.service;


import nl.fontys.playlist.database.repository.PlaylistRepository;
import nl.fontys.playlist.domain.dto.AddPlaylistDTO;
import nl.fontys.playlist.domain.dto.PlaylistDTO;
import nl.fontys.playlist.domain.models.Playlist;
import nl.fontys.playlist.rabbitmq.dto.UsernameChangedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlaylistService {

    private final PlaylistRepository repository;

    @Autowired
    public PlaylistService(PlaylistRepository repository) {
        this.repository = repository;
    }

    public List<PlaylistDTO> getAllPlaylists()
    {
        List<Playlist> playlists = repository.findAll();
        List<PlaylistDTO> reply = new ArrayList<>();
        for (Playlist playlist :
                playlists) {
            PlaylistDTO DTO = PlaylistDTO.builder()
                    .id(playlist.getExternalId())
                    .name(playlist.getName())
                    .userId(playlist.getUserId())
                    .userName(playlist.getUserName())
                    .genre(playlist.getGenre())
                    .songs(playlist.getSongs())
                    .build();
            reply.add(DTO);
        }
        return reply;
    }

    public PlaylistDTO addPlaylist(AddPlaylistDTO DTO){
        UUID userId = UUID.fromString("6fa992e4-a385-4d11-bbfa-1223e32edb80");
        Playlist playlist = new Playlist();
        playlist.setExternalId(UUID.randomUUID());
        playlist.setName(DTO.getName());
        playlist.setUserId(DTO.getUserId());
        playlist.setUserName(DTO.getUserName());
        playlist.setGenre(DTO.getGenre());

        playlist = repository.save(playlist);
        return PlaylistDTO.builder()
                .id(playlist.getExternalId())
                .name(playlist.getName())
                .userId(playlist.getUserId())
                .userName(playlist.getUserName())
                .genre(playlist.getGenre())
                .build();
    }

    public PlaylistDTO updatePlaylist(PlaylistDTO playlistDTO){
        Optional<Playlist> optPlaylist = repository.findByExternalId(playlistDTO.getId());
        Playlist playlist = optPlaylist.get();

        playlist.setName(playlistDTO.getName());
        playlist.setGenre(playlistDTO.getGenre());

        playlist = repository.save(playlist);

        return PlaylistDTO.builder()
                .id(playlist.getExternalId())
                .name(playlist.getName())
                .userId(playlist.getUserId())
                .userName(playlist.getUserName())
                .genre(playlist.getGenre())
                .songs(playlist.getSongs())
                .build();
    }

    public boolean deletePlaylist(UUID id){
        Optional<Playlist> optPlaylist = repository.findByExternalId(id);
        Playlist playlist = optPlaylist.get();

        repository.delete(playlist);
        optPlaylist = repository.findByExternalId(id);
        return optPlaylist.isEmpty();
    }

    public void updateUsername(UsernameChangedDTO dto){
        ArrayList<Playlist> playlists = repository.findByUserId(dto.getUserId());
        for (Playlist playlist :
                playlists) {
            playlist.setUserName(dto.getUsername());
        }
        repository.saveAll(playlists);
    }
}
