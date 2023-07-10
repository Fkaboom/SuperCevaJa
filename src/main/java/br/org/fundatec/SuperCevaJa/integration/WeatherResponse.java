package br.org.fundatec.SuperCevaJa.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeatherResponse {

    private String name;
    private Double temp_c;
}