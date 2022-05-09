package nl.fontys.playlist.domain.dto;

import lombok.Getter;
import lombok.Setter;
import nl.fontys.playlist.domain.models.Genre;

import java.util.UUID;

@Getter
@Setter
public class AddPlaylistDTO {
    private String name;
    private UUID userId;
    private Genre genre;
}
