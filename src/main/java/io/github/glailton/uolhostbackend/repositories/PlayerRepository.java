package io.github.glailton.uolhostbackend.repositories;

import io.github.glailton.uolhostbackend.models.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByCodiname(String codiname);
}
