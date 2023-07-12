package br.org.fundatec.SuperCevaJa.dto.beer.type;

import java.math.BigDecimal;


public class BeerTypeRequestUpdateDTO {

    private Long id;

    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
