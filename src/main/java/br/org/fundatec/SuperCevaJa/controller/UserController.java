package br.org.fundatec.SuperCevaJa.controller;

import br.org.fundatec.SuperCevaJa.dto.UserDto;
import br.org.fundatec.SuperCevaJa.model.UserModel;
import br.org.fundatec.SuperCevaJa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cevaja/api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//Mudar de Model para CreateDTO
    @PostMapping
    //public ResponseEntity<String> createUser(@RequestBody UserRequestCreateDTO userRequestCreateDTO) {
    public ResponseEntity<String> createUser(@RequestBody UserModel userModel) {
        UserModel createdUserModel = userService.createUser(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("User " + createdUserModel.getLogin()
                + " was created.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.userService.findByIdDto(id));
    }


    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(this.userService.findAll());
    }

    //Mudar de @RequestBody Map<String, String> requestBody para o updateDto
    @PutMapping("/{id}")
    //    public ResponseEntity<Void> updateUser(@PathVariable ("id") Long id, @RequestBody UserRequestUpdateDTO userRequestUpdateDTO) {
    public ResponseEntity<Void> updateUser(@PathVariable ("id") Long id, @RequestBody Map<String, String> requestBody) {

        String name = requestBody.get("name");
        String surname = requestBody.get("surname");

        UserModel updateModel = userService.updateUser(id, name,surname);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @DeleteMapping("/{login}")
    public ResponseEntity<String> deleteUser(@PathVariable("login") String login) {
        this.userService.deleteUser(login);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User " + login + " has been deleted.");
    }
}
