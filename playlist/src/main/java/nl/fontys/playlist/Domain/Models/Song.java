package nl.fontys.playlist.Domain.Models;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.GeneratedReferenceTypeDelegate;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="[song]")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private UUID externalId;
    private String title;
    private String artist;
    private Genre genre;
}
