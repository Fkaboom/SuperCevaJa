package br.org.fundatec.SuperCevaJa.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.org.fundatec.SuperCevaJa.dto.user.UserDTO;
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
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testCreateUserSuccess() {

        UserRequestCreateDTO userRequestCreateDTO = new UserRequestCreateDTO();

        userRequestCreateDTO.setName("Jilton");
        userRequestCreateDTO.setSurname("Alamis");
        userRequestCreateDTO.setPassword("mysonphilip123");
        userRequestCreateDTO.setLogin("militarmaromba");
        userRequestCreateDTO.setBirthDate(LocalDate.parse("1970-01-01"));
        userRequestCreateDTO.setCpf("011.208.800-76");

        doNothing().when(userService).createUser(userRequestCreateDTO);

        ResponseEntity<String> responseEntity = userController.createUser(userRequestCreateDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("User " + userRequestCreateDTO.getLogin() + " was created.", responseEntity.getBody());

        verify(userService, times(1)).createUser(userRequestCreateDTO);
    }

    @Test
    public void testCreateUserDuplicateLogin() {
        UserRequestCreateDTO userRequestCreateDTO = new UserRequestCreateDTO();
        userRequestCreateDTO.setName("Jilton");
        userRequestCreateDTO.setSurname("Alamis");
        userRequestCreateDTO.setPassword("mysonphilip123");
        userRequestCreateDTO.setLogin("militarmaromba");
        userRequestCreateDTO.setBirthDate(LocalDate.parse("1970-01-01"));
        userRequestCreateDTO.setCpf("011.208.800-76");

        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "User already exists with login " + userRequestCreateDTO.getLogin())).when(userService).createUser(userRequestCreateDTO);

        ResponseEntity<String> responseEntity = userController.createUser(userRequestCreateDTO);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        String expectedErrorMessage = "User already exists with login " + userRequestCreateDTO.getLogin();
        String actualErrorMessage = responseEntity.getBody().replace("\"", ""); // Remover as aspas do resultado
        assertEquals(HttpStatus.BAD_REQUEST + " " + expectedErrorMessage, actualErrorMessage);

        verify(userService, times(1)).createUser(userRequestCreateDTO);
    }

    @Test
    public void testFindByIdSuccess() {
        Long userId = 1L;

        UserDTO userDto = new UserDTO();
        when(userService.findByIdDto(userId)).thenReturn(userDto);

        ResponseEntity<UserDTO> responseEntity = userController.findById(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UserDTO expectedUserDto = userDto;
        UserDTO actualUserDto = responseEntity.getBody();
        assertEquals(expectedUserDto, actualUserDto);

        verify(userService, times(1)).findByIdDto(userId);
    }

    @Test
    public void testFindAllSuccess() {

        List<UserDTO> userDtoList = new ArrayList<>();


        UserDTO userDTO1 = new UserDTO();
        userDTO1.setName("John");
        userDTO1.setSurname("Doe");
        userDTO1.setCpf("123.456.789-00");
        userDTO1.setLogin("john.doe");

        UserDTO userDTO2 = new UserDTO();
        userDTO2.setName("Jane");
        userDTO2.setSurname("Smith");
        userDTO2.setCpf("987.654.321-00");
        userDTO2.setLogin("jane.smith");

        userDtoList.add(userDTO1);
        userDtoList.add(userDTO2);

        when(userService.findAll()).thenReturn(userDtoList);

        ResponseEntity<List<UserDTO>> responseEntity = userController.findAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<UserDTO> expectedUserDtoList = userDtoList;
        List<UserDTO> actualUserDtoList = responseEntity.getBody();
        assertEquals(expectedUserDtoList, actualUserDtoList);

        verify(userService, times(1)).findAll();
    }

    @Test
    public void testDeleteUser_Success() {
        // Defina um login de usuário válido para exclusão
        String userLoginToDelete = "john_doe";

        // Simule o comportamento do UserService para o método deleteUser
        // Considerando que a exclusão é bem-sucedida
        doNothing().when(userService).deleteUser(userLoginToDelete);

        // Chame o método da controller que você quer testar
        ResponseEntity<String> responseEntity = userController.deleteUser(userLoginToDelete);

        // Verifique se a resposta HTTP é a esperada (202 - Accepted)
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals("User " + userLoginToDelete + " has been deleted.", responseEntity.getBody());

        // Verifique se o método do UserService foi chamado com o login correto
        verify(userService, times(1)).deleteUser(userLoginToDelete);
    }


    @Test
    public void testDeleteUser_Failure() {
        // Defina um login de usuário inválido para exclusão
        String nonExistingUserLogin = "non_existing_user";

        // Simule o comportamento do UserService para o método deleteUser
        // Considerando que a exclusão lança uma exceção
        doThrow(new IllegalArgumentException("User not found with login " + nonExistingUserLogin))
                .when(userService).deleteUser(nonExistingUserLogin);

        // Chame o método da controller que você quer testar
        ResponseEntity<String> responseEntity = userController.deleteUser(nonExistingUserLogin);

        // Verifique se a resposta HTTP é a esperada (400 - Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("User not found with login " + nonExistingUserLogin, responseEntity.getBody());

        // Verifique se o método do UserService foi chamado com o login correto
        verify(userService, times(1)).deleteUser(nonExistingUserLogin);
    }
}





