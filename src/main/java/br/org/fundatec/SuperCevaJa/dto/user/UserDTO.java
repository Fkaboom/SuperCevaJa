package br.org.fundatec.SuperCevaJa.dto.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserDTO {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String cpf;

    @Column
    private String login;



}
