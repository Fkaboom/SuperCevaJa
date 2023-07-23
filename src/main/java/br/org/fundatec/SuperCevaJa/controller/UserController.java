package br.org.fundatec.SuperCevaJa.controller;

import br.org.fundatec.SuperCevaJa.dto.user.UserDTO;
import br.org.fundatec.SuperCevaJa.dto.user.UserRequestCreateDTO;
import br.org.fundatec.SuperCevaJa.dto.user.UserRequestUpdateDTO;
import br.org.fundatec.SuperCevaJa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author Felipe Brandão e João Gabriel C. da Cruz
 * Controller responsavel para lidar com as operações ligadas com Usuario
 */
@RestController
@RequestMapping("/cevaja/api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     *
     * @param userRequestCreateDTO
     * @see UserRequestCreateDTO
     * @throws HttpStatus.CREATED se funcionar
     * @throws HttpStatus.BAD_REQUEST se Login já existir ou tiver erro
     * Recebe corpo e cria usuario
     */
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequestCreateDTO userRequestCreateDTO) {

        try {
            userService.createUser(userRequestCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("User " + userRequestCreateDTO.getLogin()
                    + " was created.");
        } catch (Exception e) {
            System.err.println("Error checking user login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(e.getMessage());
        }
    }

    /**
     *
     * @param id
     * @return userService.findByIdDto
     * @throws ResponseEntity.OK se encontrar usuario
     * @throws HttpStatus.BAD_REQUEST caso usuario não exista
     * Procura usuario por id
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {

        try {
            return ResponseEntity.ok(this.userService.findByIdDto(id));
        } catch (Exception e) {
            System.err.println("Error checking user ID: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }


    /**
     @return userService.user
     Retorna lista de usuario*/
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(this.userService.findAll());
    }

    /**
     *
     * @param userRequestUpdateDTO
     * @see UserRequestUpdateDTO
     * @return userService.updateUser
     * @throws HttpStatus.NO_CONTENT caso atualize
     * @throws HttpStatus.BAD_REQUEST caso usuario não exista
     * Passa corpo e atualiza Usuario
     */
    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody UserRequestUpdateDTO userRequestUpdateDTO) {

        try {
            userService.updateUser(userRequestUpdateDTO);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            System.err.println("Error checking user ID: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(e.getMessage());
        }

    }

    /**
     *
     * @param login
     * @return userService.deleteUSer
     * @throws HttpStatus.ACCEPTED caso usuario seja encontrado e deletado
     * @throws HttpStatus.BAD_REQUEST caso usuario não exista
     * Deleta usuario a partir de login
     */
    @DeleteMapping("/{login}")
    public ResponseEntity<String> deleteUser(@PathVariable("login") String login) {

        try {
            this.userService.deleteUser(login);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("User " + login + " has been deleted.");
        } catch (Exception e) {
            System.err.println("Error checking User: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(e.getMessage());
        }

    }
}
