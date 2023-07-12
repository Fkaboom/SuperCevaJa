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
        beerTypeService.createBeerType(beerTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Beer " + beerTypeDTO.getName()
                + " was created.");
    }

    @GetMapping
    public ResponseEntity<List<BeerTypeDTO>> findAll() {
        return ResponseEntity.ok(this.beerTypeService.findAll());
    }


    @PutMapping
    public ResponseEntity<Void> updateBeerType(@RequestBody BeerTypeRequestUpdateDTO beerTypeRequestUpdateDTO) {
        beerTypeService.updateBeerType(beerTypeRequestUpdateDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteBeerType(@PathVariable("name") String name) {
        this.beerTypeService.deleteBeerType(name);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Beer " + name + " has been deleted.");
    }
}
