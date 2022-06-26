package nl.fontys.playlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import nl.fontys.playlist.config.TestSecurityConfig;
import nl.fontys.playlist.domain.dto.AddPlaylistDTO;
import nl.fontys.playlist.domain.models.Genre;
import nl.fontys.playlist.rabbitmq.dto.UsernameChangedDTO;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = PlaylistIntegrationTests.Initializer.class)
@Testcontainers
class PlaylistIntegrationTests {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Value("${fontys.rabbitmq.exchange}")
	private String exchange;
	@Value("${fontys.rabbitmq.routingkey}")
	private String routingkey;

	@Autowired
	private MockMvc mvc;

	@Container
	public static final GenericContainer rabbit = new GenericContainer(DockerImageName.parse("rabbitmq:3.8-management"))
			.withExposedPorts(5672, 15672);

	private final ObjectMapper mapper = new ObjectMapper();

	@Test
	void testGetAllPlaylists() throws Exception {
		mvc.perform(get("/playlist").with(oidcLogin()
				.authorities(new SimpleGrantedAuthority("ROLE_api-user"))))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	void testGetAllPlaylistsAnonymous() throws Exception {
		mvc.perform(get("/playlist").with(oidcLogin())).andExpect(status().isForbidden());
	}

	@Test
	public void testAddPlaylist() throws Exception {
		AddPlaylistDTO playlist = new AddPlaylistDTO();
		playlist.setName("Leuke playlist");
		playlist.setGenre(Genre.POP);
		playlist.setUserName("EenUsername");
		playlist.setUserId(UUID.fromString("e1b225a1-21d1-4c2e-89d4-b01784f48296"));
		MvcResult result = mvc.perform(post("/playlist")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(playlist))
				.characterEncoding("utf-8")
				.with(oidcLogin().authorities(new SimpleGrantedAuthority("ROLE_api-user"))))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNotEmpty())
				.andReturn();
	}

	@Test
	public void testRecieveUpdateUsernameEvent(CapturedOutput output) throws Exception {

		UsernameChangedDTO event = new UsernameChangedDTO();
		event.setUsername("NieuweUsername");
		event.setUserId(UUID.fromString("e1b225a1-21d1-4c2e-89d4-b01784f48296"));

		String eventString = mapper.writeValueAsString(event);

		rabbitTemplate.convertAndSend(exchange, routingkey, eventString);


		await().atMost(5, TimeUnit.SECONDS).until(isMessageConsumed(output), is(true));
	}

	private Callable<Boolean> isMessageConsumed(CapturedOutput output) {
		return () -> output.toString().contains("Received event in service document generation: {\"userId\":\"e1b225a1-21d1-4c2e-89d4-b01784f48296\",\"username\":\"NieuweUsername\"}");
	}

	public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			val values = TestPropertyValues.of(
					"spring.rabbitmq.host=" + rabbit.getHost(),
					"spring.rabbitmq.port=" + rabbit.getMappedPort(5672)
			);
			values.applyTo(configurableApplicationContext);
		}
	}


}
