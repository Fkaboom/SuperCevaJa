package br.org.fundatec.SuperCevaJa.dto.user;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequestCreateDTO {
    private String name;

    private String surname;

    private LocalDate birthDate;

    private String cpf;

    private String login;

    private String password;


}
