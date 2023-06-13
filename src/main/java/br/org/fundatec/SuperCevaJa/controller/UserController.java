package br.org.fundatec.SuperCevaJa.controller;

import br.org.fundatec.SuperCevaJa.model.UserModel;
import br.org.fundatec.SuperCevaJa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cevaja/user")
@CrossOrigin(origins = "*")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel){
        UserModel createdUserModel = userService.createUser(userModel);
        return ResponseEntity.ok(createdUserModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.userService.findById(id));
    }


    @GetMapping
    public ResponseEntity<List<UserModel>> findAll() {
        return ResponseEntity.ok(this.userService.findAll());
    }

    @DeleteMapping("/{id}")
    public void deleteUSer(@PathVariable("id") Long id){this.userService.deleteUser(id);}
}
