package br.org.fundatec.SuperCevaJa.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String subname;


}
