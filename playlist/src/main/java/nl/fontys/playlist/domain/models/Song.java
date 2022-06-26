package nl.fontys.playlist.domain.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="[song]")
public class Song {
    @Id
    private String id;
    private UUID externalId;
    private String title;
    private String artist;
    private Genre genre;
}
