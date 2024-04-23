package io.github.glailton.uolhostbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.glailton.uolhostbackend.common.builders.player.PlayerBuilder;
import io.github.glailton.uolhostbackend.models.entities.Player;
import io.github.glailton.uolhostbackend.services.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;
import java.util.Optional;

import static io.github.glailton.uolhostbackend.models.enums.GroupType.AVENGERS;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlayerService service;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void createPlayer_WithValidData_ReturnsCreated() throws Exception {
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
                "codiname",
                AVENGERS
        );

        when(service.createPlayer(playerDto)).thenReturn(player);

        mockMvc
                .perform(post("/players")
                        .content(mapper.writeValueAsString(playerDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(player));
    }

    @Test
    public void createPlayer_WithInvalidData_ReturnsBadRequest() throws Exception {
        var invalidPlayer = PlayerBuilder.of(1L, "", "", "", "", null);
        var emptyPlayer = new Player();

        mockMvc
                .perform(post("/players")
                        .content(mapper.writeValueAsString(emptyPlayer))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        mockMvc
                .perform(post("/players")
                        .content(mapper.writeValueAsString(invalidPlayer))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createPlayer_WithoutCodinameAvailable_ReturnsBadRequest() throws Exception {
        var playerDto = PlayerBuilder.of(
                "glailton",
                "glailton@test.com",
                "123456",
                AVENGERS
        );

        when(service.createPlayer(any())).thenThrow(NoSuchElementException.class);

        mockMvc
                .perform(post("/players")
                        .content(mapper.writeValueAsString(playerDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("{\"message\":\"Essa lista nÃ£o possui usuÃ¡rios disponÃ\u00ADveis\",\"status\":\"400\"}", result.getResponse().getContentAsString()));
    }


    @Test
    public void getPlayer_ByExistingId_ReturnsPlayers() throws Exception {
        var player = PlayerBuilder.of(
                1L,
                "glailton",
                "glailton@test.com",
                "123456",
                "codiname",
                AVENGERS
        );

        when(service.getPlayer(1L)).thenReturn(Optional.of(player));


        mockMvc
                .perform(get("/players/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(player));
    }

    @Test
    public void getPlayerById_ByNotExistingId_ReturnsNotFound() throws Exception {
        mockMvc
            .perform(get("/players/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void getPlayer_ByExistingCodinameId_ReturnsPlayers() throws Exception {
        var player = PlayerBuilder.of(
                1L,
                "glailton",
                "glailton@test.com",
                "123456",
                "codiname",
                AVENGERS
        );

        when(service.getPlayerByCodiname(player.getCodiname())).thenReturn(Optional.of(player));

        mockMvc
                .perform(get("/players/codiname/codiname"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(player));
    }

    @Test
    public void getPlayerById_ByNotExistingCodiname_ReturnsNotFound() throws Exception {
        mockMvc
                .perform(get("/players/codiname/codiname"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllPlayers_ReturnsPlayers() throws Exception {
        when(service.getAllPlayers()).thenReturn(PlayerBuilder.listBuild());

        mockMvc
            .perform(get("/players"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)));
    }


    @Test
    public void getAllPlayers_ReturnsNoPlayers() throws Exception {
        when(service.getAllPlayers()).thenReturn(emptyList());

        mockMvc
            .perform(get("/players"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void removePlayer_ByExistingId_ReturnsNoContent() throws Exception {
        mockMvc
            .perform(delete("/players/1"))
            .andExpect(status().isNoContent());
    }
}