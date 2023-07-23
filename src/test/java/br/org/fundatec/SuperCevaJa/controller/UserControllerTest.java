package br.org.fundatec.SuperCevaJa.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.org.fundatec.SuperCevaJa.dto.user.UserRequestCreateDTO;
import br.org.fundatec.SuperCevaJa.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        // Configurações antes de cada teste, se necessário
    }

    @Test
    public void testCreateUserSuccess() {
        // Crie um UserRequestCreateDTO para simular o corpo da requisição
        UserRequestCreateDTO userRequestCreateDTO = new UserRequestCreateDTO();
        userRequestCreateDTO.setName("Gilton");
        userRequestCreateDTO.setSurname("Alanis");
        userRequestCreateDTO.setPassword("mysonphilip123");
        userRequestCreateDTO.setLogin("militarmaromba");
        userRequestCreateDTO.setBirthDate(LocalDate.parse("1970-01-01"));
        userRequestCreateDTO.setCpf("011.208.800-76");

        // Simule o comportamento do UserService para o método createUser
        doNothing().when(userService).createUser(userRequestCreateDTO);

        // Chame o método da controller que você quer testar
        ResponseEntity<String> responseEntity = userController.createUser(userRequestCreateDTO);

        // Verifique se a resposta HTTP é a esperada
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("User " + userRequestCreateDTO.getLogin() + " was created.", responseEntity.getBody());

        // Verifique se o método do UserService foi chamado com os parâmetros corretos
        verify(userService, times(1)).createUser(userRequestCreateDTO);
    }

    @Test
    public void TestCreateRepeatedUserFails() {
        // Crie um UserRequestCreateDTO para simular o corpo da requisição

        UserRequestCreateDTO userRequestCreateDTO = new UserRequestCreateDTO();
        userRequestCreateDTO.setName("Jilton");
        userRequestCreateDTO.setSurname("Alamis");
        userRequestCreateDTO.setPassword("mysonphilip123");
        userRequestCreateDTO.setLogin("militarmaromba");
        userRequestCreateDTO.setBirthDate(LocalDate.parse("1970-01-01"));
        userRequestCreateDTO.setCpf("011.208.800-76");
        // Simule o comportamento do UserService para o método createUser
        doNothing().when(userService).createUser(userRequestCreateDTO);

        UserRequestCreateDTO repeatedUser = new UserRequestCreateDTO();
        repeatedUser.setName("Dio");
        repeatedUser.setSurname("Brando");
        repeatedUser.setPassword("mydadjilton123");
        userRequestCreateDTO.setLogin("militarmaromba");
        userRequestCreateDTO.setBirthDate(LocalDate.parse("2004-01-01"));
        userRequestCreateDTO.setCpf("011.209.800-76");

        doNothing().when(userService).createUser(repeatedUser);
        ResponseEntity<String> responseEntity = userController.createUser(repeatedUser);

        // Verifique se a resposta HTTP é a esperada
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("User already exists with login " + repeatedUser.getLogin(), responseEntity.getBody());

        // Verifique se o método do UserService foi chamado com os parâmetros corretos
        verify(userService, times(2)).createUser(userRequestCreateDTO);
    }

    @Test
    public void testGetUserSuccess() {
        // Crie um UserRequestCreateDTO para simular o corpo da requisição
        UserRequestCreateDTO userRequestCreateDTO = new UserRequestCreateDTO();
        userRequestCreateDTO.setName("Gilton");
        userRequestCreateDTO.setSurname("Alanis");
        userRequestCreateDTO.setPassword("mysonphilip123");
        userRequestCreateDTO.setLogin("militarmaromba");
        userRequestCreateDTO.setBirthDate(LocalDate.parse("1970-01-01"));
        userRequestCreateDTO.setCpf("011.208.800-76");

        // Simule o comportamento do UserService para o método createUser
        doNothing().when(userService).createUser(userRequestCreateDTO);

        doNothing().when(userController).findById(1L);

        ResponseEntity<String> responseEntity = userController.createUser(userRequestCreateDTO);

        // Verifique se a resposta HTTP é a esperada
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("User " + userRequestCreateDTO.getLogin() + " was created.", responseEntity.getBody());

        // Verifique se o método do UserService foi chamado com os parâmetros corretos
        verify(userService, times(1)).createUser(userRequestCreateDTO);
    }


}
