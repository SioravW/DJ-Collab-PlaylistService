package nl.fontys.playlist.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.fontys.playlist.domain.service.PlaylistService;
import nl.fontys.playlist.rabbitmq.dto.UsernameChangedDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventReceiver {

    @Autowired
    private PlaylistService service;

    private Logger log = LoggerFactory.getLogger(EventReceiver.class);

    @RabbitListener(queues = "${fontys.rabbitmq.queue}")
    public void receive(String event) throws JsonProcessingException {
        System.out.println("received the event!");
        log.info("Received event in service document generation: {}", event);
        UsernameChangedDTO dto = new ObjectMapper().readValue(event, UsernameChangedDTO.class);
        service.updateUsername(dto);
    }
}
