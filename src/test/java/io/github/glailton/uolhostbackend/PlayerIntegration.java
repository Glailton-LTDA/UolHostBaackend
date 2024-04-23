package io.github.glailton.uolhostbackend;

import io.github.glailton.uolhostbackend.common.builders.player.PlayerBuilder;
import io.github.glailton.uolhostbackend.models.entities.Player;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static io.github.glailton.uolhostbackend.models.enums.GroupType.AVENGERS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Sql(scripts = "/import_players.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/remove_players.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PlayerIntegration {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createPlayer_ReturnsCreated() {
        var playerDto = PlayerBuilder.of(
                "glailton",
                "glailton@test.com",
                "123456",
                AVENGERS
        );

        var response = restTemplate.postForEntity("/players", playerDto, Player.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("glailton");
        assertThat(response.getBody().getEmail()).isEqualTo("glailton@test.com");
        assertThat(response.getBody().getPhoneNumber()).isEqualTo("123456");
        assertThat(response.getBody().getGroupType()).isEqualTo(AVENGERS);
        assertThat(response.getBody().getCodiname()).isNotEmpty();
    }

    @Disabled
    @Test
    public void getPlayer_ReturnsPlayer() {
        var response = restTemplate.getForEntity("/players/1", Player.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getPlayerByCodiname_ReturnsPlayer() {
        var response = restTemplate.getForEntity("/players/codiname/flash", Player.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void listPlayers_ReturnsAllPlayers() {
        ResponseEntity<Player[]> response = restTemplate.getForEntity("/players", Player[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().length).isEqualTo(3);
    }

    @Disabled
    @Test
    public void removePlayer_ReturnsNoContent() {
        var response = restTemplate.exchange("players/100", HttpMethod.DELETE, null, void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
