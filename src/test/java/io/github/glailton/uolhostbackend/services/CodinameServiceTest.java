package io.github.glailton.uolhostbackend.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CodinameServiceTest {
    @InjectMocks
    private CodinameService codinameService;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private Environment env;

    @Test
    public void loadJsonData_WithValidData_ReturnsData() {
        var jsonResponse = "{\"vingadores\":[{\"codinome\":\"Hulk\"},{\"codinome\":\"CapitãoAmérica\"},{\"codinome\":\"PanteraNegra\"},{\"codinome\":\"HomemdeFerro\"},{\"codinome\":\"Thor\"},{\"codinome\":\"FeiticeiraEscarlate\"},{\"codinome\":\"Visão\"}]}";

        when(env.getProperty("avengers")).thenReturn("url");
        when(restTemplate.getForObject("url", String.class)).thenReturn(jsonResponse);

        codinameService.loadJsonData();

        var list = codinameService.getAvengersCodinameList();

        assertThat(list.size()).isEqualTo(7);
    }
}