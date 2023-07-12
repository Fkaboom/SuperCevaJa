package br.org.fundatec.SuperCevaJa.dto.beer.type;

import java.math.BigDecimal;


public class BeerTypeRequestUpdateDTO {

    private Long id;

    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
