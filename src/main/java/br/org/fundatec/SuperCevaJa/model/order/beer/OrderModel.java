package br.org.fundatec.SuperCevaJa.model.order.beer;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String login;

    @OneToMany(mappedBy = "orderModel", cascade = CascadeType.ALL)
    private List<BeerModel> beersOrder;

}
