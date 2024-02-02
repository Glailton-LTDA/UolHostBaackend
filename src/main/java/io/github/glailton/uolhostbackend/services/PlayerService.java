package io.github.glailton.uolhostbackend.services;

import io.github.glailton.uolhostbackend.infra.CodinameHandler;
import io.github.glailton.uolhostbackend.models.dtos.PlayerDto;
import io.github.glailton.uolhostbackend.models.entities.Player;
import io.github.glailton.uolhostbackend.models.enums.GroupType;
import io.github.glailton.uolhostbackend.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository repository;
    private final CodinameHandler handler;

    public PlayerService(PlayerRepository repository, CodinameHandler handler) {
        this.repository = repository;
        this.handler = handler;
    }

    public Player createPlayer(PlayerDto dto) {
        Player newPlayer = new Player(dto);
        String codiname = getCodiname(dto.groupType());
        newPlayer.setCodiname(codiname);

        return repository.save(newPlayer);
    }

    public List<Player> getAllPlayers() {
        return repository.findAll();
    }

    private String getCodiname(GroupType groupType) {
        return handler.findCodiname(groupType);
    }
}
