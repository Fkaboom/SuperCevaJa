package br.org.fundatec.SuperCevaJa.dto.beer.type;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BeerTypeRequestUpdateDTO {

    private Long id;

    private BigDecimal price;


}
