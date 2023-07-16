package br.org.fundatec.SuperCevaJa.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeatherResponse {

    private String name;
    private double temp_c;
}