package nl.fontys.playlist.domain.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Document("playlist")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {

    @Id
    @GeneratedValue
    private String id;

    private UUID externalId;
    private String name;
    private Genre genre;
    private UUID userId;
    private String userName;

    @ManyToMany
    private List<Song> songs;
}
