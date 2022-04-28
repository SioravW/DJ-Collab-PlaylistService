package nl.fontys.playlist.API.Controllers;


import nl.fontys.playlist.Domain.Service.PlaylistService;
import nl.fontys.playlist.Domain.Service.SongService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class SongController {

    private final SongService service;

    public SongController(SongService service) {
        this.service = service;
    }
}
