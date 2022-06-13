package nl.fontys.playlist.api.controllers;

import nl.fontys.playlist.domain.dto.AddPlaylistDTO;
import nl.fontys.playlist.domain.dto.PlaylistDTO;
import nl.fontys.playlist.domain.service.PlaylistService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(path = "/playlist")
@RestController
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('api-user')")
public class PlaylistController {

    private final PlaylistService service;

    public PlaylistController(PlaylistService service) {
        this.service = service;
    }

    @GetMapping
    public List<PlaylistDTO> getAllPlaylists() {
        return service.getAllPlaylists();
    }

    @PostMapping
    public PlaylistDTO addPlaylist(@RequestBody AddPlaylistDTO playlist) {
        return service.addPlaylist(playlist);
    }

    @PutMapping
    public PlaylistDTO updatePlaylist(@RequestBody PlaylistDTO playlist) {
        return service.updatePlaylist(playlist);
    }

    @DeleteMapping(value = "/{id}")
    public boolean deletePlaylist(@PathVariable UUID id) { return service.deletePlaylist(id); }
}
