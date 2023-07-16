package br.org.fundatec.SuperCevaJa.dto.user;


import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class UserDTO {

    private String name;
    private String surname;
    private String cpf;
    private String login;

}
