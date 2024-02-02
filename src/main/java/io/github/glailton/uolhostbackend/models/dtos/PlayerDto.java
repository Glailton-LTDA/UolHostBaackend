package io.github.glailton.uolhostbackend.models.dtos;

import io.github.glailton.uolhostbackend.models.enums.GroupType;

public record PlayerDto(
        String name,
        String email,
        String phoneNumber,
        GroupType groupType
) {}
