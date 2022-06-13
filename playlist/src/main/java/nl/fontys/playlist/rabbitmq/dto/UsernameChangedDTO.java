package nl.fontys.playlist.rabbitmq.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UsernameChangedDTO {
    private UUID userId;
    private String username;
}
