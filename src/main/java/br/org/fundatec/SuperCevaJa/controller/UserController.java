package br.org.fundatec.SuperCevaJa.controller;

import br.org.fundatec.SuperCevaJa.dto.UserDto;
import br.org.fundatec.SuperCevaJa.model.UserModel;
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
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) {
        UserModel createdUserModel = userService.createUser(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.userService.findByIdDto(id));
    }


    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(this.userService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable ("id") Long id, String name, String surname) {

        UserModel updateModel = userService.updateUser(id, name,surname);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @DeleteMapping("/{login}")
    public ResponseEntity<Void> deleteUser(@PathVariable("login") String login) {
        this.userService.deleteUser(login);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
