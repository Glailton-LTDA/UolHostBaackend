package io.github.glailton.uolhostbackend.common.builders.player;

import io.github.glailton.uolhostbackend.models.dtos.PlayerDto;
import io.github.glailton.uolhostbackend.models.entities.Player;
import io.github.glailton.uolhostbackend.models.enums.GroupType;

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
}
