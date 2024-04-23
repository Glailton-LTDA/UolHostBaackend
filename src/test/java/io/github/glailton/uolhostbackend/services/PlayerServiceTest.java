package io.github.glailton.uolhostbackend.services;

import io.github.glailton.uolhostbackend.common.builders.player.PlayerBuilder;
import io.github.glailton.uolhostbackend.infra.CodinameHandler;
import io.github.glailton.uolhostbackend.models.entities.Player;
import io.github.glailton.uolhostbackend.repositories.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static io.github.glailton.uolhostbackend.models.enums.GroupType.AVENGERS;
import static io.github.glailton.uolhostbackend.models.enums.GroupType.JUSTICE_LEAGUE;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {
    @InjectMocks
    private PlayerService playerService;

    @Mock
    private CodinameHandler handler;

    @Mock
    private PlayerRepository repository;

    @Test()
    public void createPlayer_WithValidData_ReturnsPlayer() {
        var codiname = "homem de ferro";
        var playerDto = PlayerBuilder.of(
                "glailton",
                "glailton@test.com",
                "123456",
                AVENGERS
        );
        var player = PlayerBuilder.of(
                1L,
                "glailton",
                "glailton@test.com",
                "123456",
                codiname,
                AVENGERS
        );

        when(handler.findCodiname(playerDto.groupType())).thenReturn(codiname);
        when(repository.save(any())).thenReturn(player);

        Player response = playerService.createPlayer(playerDto);

        assertThat(response.getId()).isGreaterThan(0);
        assertThat(response.getName()).isEqualTo(playerDto.name());
        assertThat(response.getEmail()).isEqualTo(playerDto.email());
        assertThat(response.getPhoneNumber()).isEqualTo(playerDto.phoneNumber());
        assertThat(response.getGroupType()).isEqualTo(playerDto.groupType());
        assertThat(response.getCodiname()).isEqualTo(codiname);
    }

    @Test
    public void getAllPlayers_WithValidData_ReturnsPlayers() {
        var players = List.of(
                PlayerBuilder.of(1L, "glailton", "glailton@test.com", "123456", "hulk", AVENGERS),
                PlayerBuilder.of(2L, "joao", "joao@test.com", "123456", "superman", JUSTICE_LEAGUE),
                PlayerBuilder.of(3L, "maria", "maria@test.com", "123456", "mulher maravilha", AVENGERS)
        );

        when(repository.findAll()).thenReturn(players);

        var response = playerService.getAllPlayers();

        assertThat(response.size()).isEqualTo(players.size());
    }

    @Test
    public void getAllPlayers_WithValidData_ReturnsEmpty() {
        when(repository.findAll()).thenReturn(List.of());

        var response = playerService.getAllPlayers();

        assertThat(response.size()).isEqualTo(0);
    }

    @Test
    public void createPlayer_WithInvalidData_ThrowsException() {
        var invalidPlayer = PlayerBuilder.of(1L, "", "", "", "", null);

        var invalidPlayerDto = PlayerBuilder.of(
                "",
                "",
                "",
                null
        );
        when(repository.save(invalidPlayer)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> playerService.createPlayer(invalidPlayerDto))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlayerById_ByExistingId_ReturnsPlayer() {
        var player = PlayerBuilder.of(
                1L,
                "glailton",
                "glailton@test.com",
                "123456",
                "codiname",
                AVENGERS
        );

        when(repository.findById(player.getId())).thenReturn(Optional.of(player));

        var response = playerService.getPlayer(player.getId());

        assertThat(response).isNotEmpty();
        assertThat(response.get()).isEqualTo(player);
    }

    @Test
    public void getPlayerById_ByNotExistingId_ReturnsEmpty() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        var response = playerService.getPlayer(1L);
        assertThat(response).isEmpty();
    }

    @Test
    public void getPlayerByCodiname_ByExistingCodiname_ReturnsPlayer() {
        var player = PlayerBuilder.of(
                1L,
                "glailton",
                "glailton@test.com",
                "123456",
                "codiname",
                AVENGERS
        );

        when(repository.findByCodiname(player.getCodiname())).thenReturn(Optional.of(player));

        var response = playerService.getPlayerByCodiname(player.getCodiname());

        assertThat(response).isNotEmpty();
        assertThat(response.get()).isEqualTo(player);
    }

    @Test
    public void getPlayerByCodiname_ByNotExistingCodiname_ReturnsEmpty() {
        when(repository.findByCodiname("codiname")).thenReturn(Optional.empty());

        var response = playerService.getPlayerByCodiname("codiname");
        assertThat(response).isEmpty();
    }

    @Test
    public void removePlayer_ByExistingId_doesNotThrowAnyException() {
        assertThatCode(() -> playerService.removePlayer(1L)).doesNotThrowAnyException();
    }

    @Test
    public void removePlayer_ByNotExistingId_ThrowsException() {
        doThrow(new RuntimeException()).when(repository).deleteById(19L);

        assertThatThrownBy(() -> playerService.removePlayer(19L)).isInstanceOf(RuntimeException.class);
    }

}