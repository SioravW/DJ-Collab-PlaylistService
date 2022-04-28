package nl.fontys.playlist.Domain.Models;


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
    private long userId;

    @ManyToMany
    private List<Song> songs;
}
