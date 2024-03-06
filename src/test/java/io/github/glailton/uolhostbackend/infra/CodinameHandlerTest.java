package io.github.glailton.uolhostbackend.infra;

import io.github.glailton.uolhostbackend.models.enums.GroupType;
import io.github.glailton.uolhostbackend.services.CodinameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CodinameHandlerTest {
    @InjectMocks
    private CodinameHandler handler;
    @Mock
    private CodinameService service;

    @Test
    void findCodiname_WithGroupTypeAvengers_ReturnsCodiname() {
        var groupType = GroupType.AVENGERS;
        var list = new ArrayList<String>();
        list.add("1");

        when(service.getAvengersCodinameList()).thenReturn(list);

        var response = handler.findCodiname(groupType);

        assertThat(response).isEqualTo("1");
    }

    @Test
    void findCodiname_WithGroupTypeAvengers_ThrowsException() {
        var groupType = GroupType.AVENGERS;
        var list = new ArrayList<String>();

        when(service.getAvengersCodinameList()).thenReturn(list);

        assertThatThrownBy(() -> handler.findCodiname(groupType))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void findCodiname_WithGroupTypeJusticeLeague_ReturnsCodiname() {
        var groupType = GroupType.JUSTICE_LEAGUE;
        var list = new ArrayList<String>();
        list.add("1");

        when(service.getJusticeLeagueCodinameList()).thenReturn(list);

        var response = handler.findCodiname(groupType);

        assertThat(response).isEqualTo("1");
    }

    @Test
    void findCodiname_WithGroupTypeJusticeLeague_ThrowsException() {
        var groupType = GroupType.JUSTICE_LEAGUE;
        var list = new ArrayList<String>();

        when(service.getJusticeLeagueCodinameList()).thenReturn(list);

        assertThatThrownBy(() -> handler.findCodiname(groupType))
                .isInstanceOf(NoSuchElementException.class);
    }
}