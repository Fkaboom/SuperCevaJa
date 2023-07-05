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

    public WeatherResponse weatherResponse(String name){
        String urlCompleta = this.uri + "/" +"name";
        WeatherResponse weatherResponse = this.restTemplate.getForObject(urlCompleta, WeatherResponse.class);
        return weatherResponse;
    }

    public double findWeather(){
        return 2;
    }


}
