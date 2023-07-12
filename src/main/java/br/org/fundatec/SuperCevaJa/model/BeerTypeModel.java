package br.org.fundatec.SuperCevaJa.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class BeerTypeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    @Column(nullable = true)
    private LocalDateTime deletedAt;


}
