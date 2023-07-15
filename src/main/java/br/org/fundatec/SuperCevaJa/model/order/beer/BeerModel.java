package br.org.fundatec.SuperCevaJa.model.order.beer;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class BeerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String nameType;

    @Column
    private BigDecimal quantity;

    @ManyToOne
    @JoinColumn(name = "OrderModel_id")
    private OrderModel orderModel;
}
