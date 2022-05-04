package nl.fontys.playlist.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddPlaylistDTO {
    private String name;
    private UUID userId;
}
