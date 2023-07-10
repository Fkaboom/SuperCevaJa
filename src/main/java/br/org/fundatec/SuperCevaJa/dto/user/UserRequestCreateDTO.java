package br.org.fundatec.SuperCevaJa.dto.user;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class UserRequestCreateDTO {
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
}
