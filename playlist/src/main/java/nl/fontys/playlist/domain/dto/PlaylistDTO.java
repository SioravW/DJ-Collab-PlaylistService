package nl.fontys.playlist.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nl.fontys.playlist.domain.models.Genre;
import nl.fontys.playlist.domain.models.Song;

import javax.persistence.ManyToMany;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class PlaylistDTO {

    private UUID id;
    private String name;
    private Genre genre;
    private UUID userId;
    private String userName;

    private List<Song> songs;
}
