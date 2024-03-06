package io.github.glailton.uolhostbackend.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ControllerExceptionHandlerTest {

    @InjectMocks
    private ControllerExceptionHandler handler;
    @Test
    void threatNoSuchElementException() {
        var exception = handler.threatNoSuchElementException(new NoSuchElementException());

        assertEquals(exception.getStatusCode().toString(), "400 BAD_REQUEST");
    }
}