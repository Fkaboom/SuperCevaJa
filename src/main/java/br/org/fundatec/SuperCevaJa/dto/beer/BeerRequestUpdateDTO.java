package br.org.fundatec.SuperCevaJa.dto.beer;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class BeerRequestUpdateDTO {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private BigDecimal price;
}
