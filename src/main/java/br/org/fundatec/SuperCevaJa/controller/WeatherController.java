package br.org.fundatec.SuperCevaJa.controller;

import br.org.fundatec.SuperCevaJa.integration.WeatherIntegrationService;
import br.org.fundatec.SuperCevaJa.integration.WeatherResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cevaja/api/v1/weather")
@CrossOrigin(origins = "*")
public class WeatherController {

private WeatherIntegrationService weatherIntegrationService;

    public WeatherController(WeatherIntegrationService weatherIntegrationService) {
        this.weatherIntegrationService = weatherIntegrationService;
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<WeatherResponse> findByName(@PathVariable("name") String name){
        return ResponseEntity.ok(this.weatherIntegrationService.findByName(name));
    }
}
