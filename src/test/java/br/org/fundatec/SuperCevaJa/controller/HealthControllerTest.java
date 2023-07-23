package br.org.fundatec.SuperCevaJa.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class HealthControllerTest {
    @InjectMocks
    HealthController healthController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("SuperCevaJaAPI - Health Check")
    public void HealthyAPITest() {
        ResponseEntity result = healthController.sayHello();
        assertEquals(ResponseEntity.ok("Hi! I'm RestTemplate POC!"), result);
    }
}
