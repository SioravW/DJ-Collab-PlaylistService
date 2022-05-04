package nl.fontys.playlist.api.controllers;


import nl.fontys.playlist.domain.service.SongService;
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
