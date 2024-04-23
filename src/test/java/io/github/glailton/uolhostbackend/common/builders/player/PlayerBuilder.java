package io.github.glailton.uolhostbackend.common.builders.player;

import io.github.glailton.uolhostbackend.models.dtos.PlayerDto;
import io.github.glailton.uolhostbackend.models.entities.Player;
import io.github.glailton.uolhostbackend.models.enums.GroupType;

import java.util.ArrayList;
import java.util.List;

import static io.github.glailton.uolhostbackend.models.enums.GroupType.AVENGERS;
import static io.github.glailton.uolhostbackend.models.enums.GroupType.JUSTICE_LEAGUE;

public record PlayerBuilder(
        String name,
        String email,
        String phoneNumber,
        GroupType groupType
) {
    public PlayerBuilder(String name, String email, String phoneNumber,GroupType groupType) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.groupType = groupType;
    }

    public static PlayerDto of(String name, String email, String phoneNumber, GroupType groupType) {
        return new PlayerDto(name, email, phoneNumber, groupType);
    }

    public static Player of(Long id, String name, String email, String phoneNumber, String codiname, GroupType groupType) {
        return new Player(id, name, email, phoneNumber, codiname, groupType);
    }

    public static List<Player> listBuild() {
        var player1 = PlayerBuilder.of(
                1L,
                "glailton",
                "glailton@test.com",
                "123456",
                "flash",
                JUSTICE_LEAGUE
        );

        var player2 = PlayerBuilder.of(
                2L,
                "jose",
                "jose@test.com",
                "123456",
                "mulher maravilha",
                JUSTICE_LEAGUE
        );

        var player3 = PlayerBuilder.of(
                3L,
                "maria",
                "maria@test.com",
                "123456",
                "homem de ferro",
                AVENGERS
        );

        return new ArrayList<>() {
            {
                add(player1);
                add(player2);
                add(player3);
            }
        };
    }
}
