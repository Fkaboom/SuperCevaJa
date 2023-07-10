package br.org.fundatec.SuperCevaJa.controller;

import br.org.fundatec.SuperCevaJa.dto.user.UserDTO;
import br.org.fundatec.SuperCevaJa.dto.user.UserRequestCreateDTO;
import br.org.fundatec.SuperCevaJa.dto.user.UserRequestUpdateDTO;
import br.org.fundatec.SuperCevaJa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cevaja/api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequestCreateDTO userRequestCreateDTO) {
        userService.createUser(userRequestCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User " + userRequestCreateDTO.getLogin()
                + " was created.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.userService.findByIdDto(id));
    }


    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(this.userService.findAll());
    }


    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody UserRequestUpdateDTO userRequestUpdateDTO) {
        userService.updateUser(userRequestUpdateDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @DeleteMapping("/{login}")
    public ResponseEntity<String> deleteUser(@PathVariable("login") String login) {
        this.userService.deleteUser(login);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User " + login + " has been deleted.");
    }
}
