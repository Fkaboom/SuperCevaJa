package br.org.fundatec.SuperCevaJa.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WeatherIntegrationService {

    private final RestTemplate restTemplate;

    @Value("${weather-external-api}")
    private String uri;

    public WeatherIntegrationService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public WeatherResponse findByName(String name) {
        String urlComplete = this.uri + "=" + name;
        WeatherResponse weatherResponse = this.restTemplate.getForObject(urlComplete, WeatherResponse.class);
        returnTemp(weatherResponse);
        return weatherResponse;
    }



    public double returnTemp(WeatherResponse weatherResponse){
        double temp = weatherResponse.getTemp_c();
        return temp;
    }


}
