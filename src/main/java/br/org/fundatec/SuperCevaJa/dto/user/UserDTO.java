package br.org.fundatec.SuperCevaJa.dto.user;




public class UserDTO {

    private String name;
    private String surname;
    private String cpf;
    private String login;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
