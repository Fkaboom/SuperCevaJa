package br.org.fundatec.SuperCevaJa.dto.user;

import java.time.LocalDate;

public class UserRequestCreateDTO {
    private String name;

    private String surname;

    private LocalDate birthDate;

    private String cpf;

    private String login;

    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }


    public LocalDate getBirthDate() {
        return birthDate;
    }


    public String getCpf() {
        return cpf;
    }



    public String getLogin() {
        return login;
    }


    public String getPassword() {
        return password;
    }

}
