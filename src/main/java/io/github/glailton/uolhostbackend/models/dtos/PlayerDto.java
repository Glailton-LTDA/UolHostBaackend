package io.github.glailton.uolhostbackend.models.dtos;

import io.github.glailton.uolhostbackend.models.enums.GroupType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record PlayerDto(
        @NotEmpty
        @Column(nullable = false)
        String name,
        @Email
        @NotEmpty
        @Column(nullable = false)
        String email,
        @Column(nullable = false)
        String phoneNumber,
        @Column(nullable = false)
        GroupType groupType
) {}
