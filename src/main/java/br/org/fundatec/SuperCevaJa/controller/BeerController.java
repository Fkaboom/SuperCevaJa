package br.org.fundatec.SuperCevaJa.controller;
//
//import br.org.fundatec.SuperCevaJa.dto.beer.BeerRequestCreateDTO;
//import br.org.fundatec.SuperCevaJa.dto.user.UserDTO;
//import br.org.fundatec.SuperCevaJa.dto.user.UserRequestUpdateDTO;
//import br.org.fundatec.SuperCevaJa.model.BeerModel;
//import br.org.fundatec.SuperCevaJa.service.BeerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/cevaja/api/v1/beer/types")
//@CrossOrigin(origins = "*")
//public class BeerController {
//    private BeerService beerService;
//
//    @PostMapping
//    public ResponseEntity<String> createBeer(@RequestBody BeerRequestCreateDTO beerRequestCreateDTO) {
//        beerService.createBeer(beerRequestCreateDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body("User " + beerRequestCreateDTO.getLogin()
//                + " was created.");
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(this.userService.findByIdDto(id));
//    }
//
//
//    @GetMapping
//    public ResponseEntity<List<UserDTO>> findAll() {
//        return ResponseEntity.ok(this.userService.findAll());
//    }
//
//
//    @PutMapping
//    public ResponseEntity<Void> updateUser(@RequestBody UserRequestUpdateDTO userRequestUpdateDTO) {
//        userService.updateUser(userRequestUpdateDTO);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//
//    }
//
//    @DeleteMapping("/{login}")
//    public ResponseEntity<String> deleteUser(@PathVariable("login") String login) {
//        this.userService.deleteUser(login);
//        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User " + login + " has been deleted.");
//    }
//
//    @Autowired
//    public BeerController(BeerService beerService) {
//        this.beerService = beerService;
//    }
//
//    @PostMapping
//    public ResponseEntity<BeerModel> createBeer(@RequestBody BeerModel beerModel) {
//        BeerModel createdBeerModel = beerService.createBeer(beerModel);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdBeerModel);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<BeerModel> updateBeer(@PathVariable ("id") Long id, @RequestBody BeerModel beerModel) {
//        BeerModel updateModel = beerService.findById(id);
//        updateModel = beerService.updateBeer(beerModel);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(updateModel);
//
//    }
//    @GetMapping
//    public ResponseEntity<List<BeerModel>> findAll() {
//        return ResponseEntity.ok(this.beerService.findAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<BeerModel> findById(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(this.beerService.findById(id));
//    }
//
//    @DeleteMapping("/{name}")
//    public void deleteBeer(@PathVariable("name") String name) {
//        this.beerService.deleteBeer(name);
//    }
//
//
//}
