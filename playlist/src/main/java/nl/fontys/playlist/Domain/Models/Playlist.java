package nl.fontys.playlist.Domain.Models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="[playlist]")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String name;

    @ManyToMany
    List<Song> songs;
}
