package br.org.fundatec.SuperCevaJa.model;
import br.org.fundatec.SuperCevaJa.model.order.beer.BeerModel;
import br.org.fundatec.SuperCevaJa.model.order.beer.OrderModel;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private LocalDate birthDate;

    @Column
    private String cpf;

    @Column
    private String login;

    @Column
    private String password;

    @Column(nullable = true)
    private LocalDateTime deletedAt;

    //AJUSATR O CODIGO
    @OneToMany(mappedBy = "userModel", cascade = CascadeType.ALL)
    private List<OrderModel> orderModels;

}
