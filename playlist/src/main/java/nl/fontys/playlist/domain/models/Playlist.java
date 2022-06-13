package nl.fontys.playlist.domain.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="[playlist]")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private UUID externalId;
    private String name;
    private Genre genre;
    private UUID userId;
    private String userName;

    @ManyToMany
    private List<Song> songs;
}
