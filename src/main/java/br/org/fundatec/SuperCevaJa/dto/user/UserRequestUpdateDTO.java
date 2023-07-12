package br.org.fundatec.SuperCevaJa.dto.user;

public class UserRequestUpdateDTO {
    private Long id;

    private String name;

    private String surname;

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

}
