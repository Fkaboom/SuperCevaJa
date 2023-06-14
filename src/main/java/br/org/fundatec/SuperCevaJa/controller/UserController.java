package br.org.fundatec.SuperCevaJa.controller;

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
    public ResponseEntity<UserModel> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.userService.findById(id));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<UserModel> updateUser(@PathVariable ("id") Long id, @RequestBody UserModel userModel) {
//        UserModel updateModel = userService.findById(id);
//        updateModel = userService.updateUser(userModel);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(updateModel);
//
//    }

    @GetMapping
    public ResponseEntity<List<UserModel>> findAll() {
        return ResponseEntity.ok(this.userService.findAll());
    }

    @DeleteMapping("/{login}")
    public void deleteUser(@PathVariable("login") String login) {
        this.userService.deleteUser(login);
    }
}
