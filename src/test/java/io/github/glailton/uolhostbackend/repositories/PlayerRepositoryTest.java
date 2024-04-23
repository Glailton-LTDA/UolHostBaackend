package io.github.glailton.uolhostbackend.repositories;

import io.github.glailton.uolhostbackend.common.builders.player.PlayerBuilder;
import io.github.glailton.uolhostbackend.models.entities.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import static io.github.glailton.uolhostbackend.models.enums.GroupType.AVENGERS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class PlayerRepositoryTest {
    @Autowired
    private PlayerRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void afterEach() {
        player.setId(null);
    }

    private Player player = PlayerBuilder.of(
            0L,
            "glailton",
            "glailton@test.com",
            "123456",
            "codiname",
            AVENGERS
    );

    @Test
    public void createPlayer_WithValidData_ReturnsPlayer() {
        var response = repository.save(player);

        var playerSaved = testEntityManager.find(Player.class, response.getId());

        assertThat(playerSaved).isNotNull();
        assertThat(playerSaved.getId()).isGreaterThan(1L);
        assertThat(playerSaved.getName()).isEqualTo(player.getName());
        assertThat(playerSaved.getEmail()).isEqualTo(player.getEmail());
        assertThat(playerSaved.getCodiname()).isEqualTo(player.getCodiname());
    }

    @Test
    public void createPlayer_WithInvalidData_ThrowsException() {
        var invalidPlayer = PlayerBuilder.of(1L, "", "", "", "", null);
        var emptyPlayer = new Player();

        assertThatThrownBy(() -> repository.save(invalidPlayer)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> repository.save(emptyPlayer)).isInstanceOf(RuntimeException.class);

    }

    @Test
    public void getPlayer_ByExistingId_ReturnsPlayers() {
        var playerDto = PlayerBuilder.of(
                "glailton",
                "glailton@test.com",
                "123456",
                AVENGERS
        );
        Player newPlayer = new Player(playerDto);
        newPlayer.setCodiname(player.getCodiname());

        var savedPlayer = testEntityManager.persistFlushFind(newPlayer);

        var response = repository.findById(savedPlayer.getId());

        assertThat(response).isNotNull();
        assertThat(response.get()).isEqualTo(savedPlayer);
    }

    @Test
    public void getPlayerById_ByNotExistingId_ReturnsEmpty() {
        var response = repository.findById(1L);

        assertThat(response).isEmpty();
    }

    @Test
    public void getPlayer_ByExistingCodiname_ReturnsPlayers() {
        var playerDto = PlayerBuilder.of(
                "glailton",
                "glailton@test.com",
                "123456",
                AVENGERS
        );
        Player newPlayer = new Player(playerDto);
        newPlayer.setCodiname(player.getCodiname());

        var savedPlayer = testEntityManager.persistFlushFind(newPlayer);

        var response = repository.findByCodiname(player.getCodiname());

        assertThat(response).isNotNull();
        assertThat(response.get()).isEqualTo(savedPlayer);
    }

    @Test
    public void getPlayerById_ByNotExistingCodiname_ReturnsEmpty() {
        var response = repository.findByCodiname("codiname");

        assertThat(response).isEmpty();
    }

    @Sql(scripts = "/import_players.sql")
    @Test
    public void getAllPlayers_ReturnsPlayers() {
        var players = PlayerBuilder.listBuild();

        var response = repository.findAll();

        assertThat(response).isNotNull();
        assertThat(response).hasSize(3);
    }

    @Test
    public void getAllPlayers_ReturnsNoPlayers() {
        var response = repository.findAll();

        assertThat(response).isNotNull();
        assertThat(response).hasSize(0);
    }

    @Test
    public void removePlayer_ByExistingId_RemovesPlayerFromDatabase() {
        var playerDto = PlayerBuilder.of(
                "glailton",
                "glailton@test.com",
                "123456",
                AVENGERS
        );
        Player newPlayer = new Player(playerDto);
        newPlayer.setCodiname(player.getCodiname());

        var savedPlayer = testEntityManager.persistFlushFind(newPlayer);

        repository.deleteById(savedPlayer.getId());

        var removedPlayer = testEntityManager.find(Player.class, savedPlayer.getId());

        assertThat(removedPlayer).isNull();
    }

}