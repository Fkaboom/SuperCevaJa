package br.org.fundatec.SuperCevaJa.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * @author Felipe Brandão e João Gabriel c. da Cruz
 */
@Service
public class WeatherIntegrationService {

    private final RestTemplate restTemplate;

    @Value("${weather-external-api}")
    private String uri;

    public WeatherIntegrationService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     *
     * @return temp em double
     * Busca pela cidade na API e retorna a temperatura em graus
     */
    public WeatherResponse findCity() {
        String urlComplete = this.uri;
        WeatherResponse weatherResponse = this.restTemplate.getForObject(urlComplete, WeatherResponse.class);
        returnTemp(weatherResponse);
        return weatherResponse;
    }


    /**
     *
     * @param weatherResponse
     * @return temp em double
     */
    public double returnTemp(WeatherResponse weatherResponse){
        double temp = weatherResponse.getTemp_c();
        return temp;
    }


}
