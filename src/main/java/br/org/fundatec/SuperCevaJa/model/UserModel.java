package br.org.fundatec.SuperCevaJa.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

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



}
