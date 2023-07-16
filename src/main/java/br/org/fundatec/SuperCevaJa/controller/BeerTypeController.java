package br.org.fundatec.SuperCevaJa.controller;

import br.org.fundatec.SuperCevaJa.dto.beer.type.BeerTypeDTO;
import br.org.fundatec.SuperCevaJa.dto.beer.type.BeerTypeRequestUpdateDTO;
import br.org.fundatec.SuperCevaJa.service.BeerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cevaja/api/v1/beer/types")
@CrossOrigin(origins = "*")
public class BeerTypeController {
    private BeerTypeService beerTypeService;

    @Autowired
    public BeerTypeController(BeerTypeService beerTypeService) {
        this.beerTypeService = beerTypeService;
    }

    @PostMapping
    public ResponseEntity<String> createBeerType(@RequestBody BeerTypeDTO beerTypeDTO) {
        try {
            beerTypeService.createBeerType(beerTypeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Beer " + beerTypeDTO.getName()
                    + " was created.");
        } catch (Exception e) {
            System.err.println("Error checking Beer Type: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<List<BeerTypeDTO>> findAll() {
        return ResponseEntity.ok(this.beerTypeService.findAll());
    }


    @PutMapping
    public ResponseEntity<String> updateBeerType(@RequestBody BeerTypeRequestUpdateDTO beerTypeRequestUpdateDTO) {

        try {
            beerTypeService.updateBeerType(beerTypeRequestUpdateDTO);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            System.err.println("Error checking beer type ID: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(e.getMessage());
        }

    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteBeerType(@PathVariable("name") String name) {
        try {
            this.beerTypeService.deleteBeerType(name);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Beer " + name + " has been deleted.");
        } catch (Exception e) {
            System.err.println("Error checking Beer Type: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(e.getMessage());
        }
    }
}
