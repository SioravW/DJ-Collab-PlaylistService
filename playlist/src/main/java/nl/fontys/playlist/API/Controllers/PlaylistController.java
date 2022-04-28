package nl.fontys.playlist.API.Controllers;

import nl.fontys.playlist.Domain.Models.Playlist;
import nl.fontys.playlist.Domain.Service.PlaylistService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PlaylistController {

    private final PlaylistService service;

    public PlaylistController(PlaylistService service) {
        this.service = service;
    }

    @GetMapping(value = "/user/all")
    public List<Playlist> GetAllPlaylists() {
        return service.getAllPlaylists();
    }
}
