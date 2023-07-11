package br.org.fundatec.SuperCevaJa.controller;

import br.org.fundatec.SuperCevaJa.dto.beer.BeerDTO;
import br.org.fundatec.SuperCevaJa.dto.beer.BeerRequestUpdateDTO;
import br.org.fundatec.SuperCevaJa.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cevaja/api/v1/beer/types")
@CrossOrigin(origins = "*")
public class BeerController {
    private BeerService beerService;

    @Autowired
    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @PostMapping
    public ResponseEntity<String> createBeer(@RequestBody BeerDTO beerDTO) {
        beerService.createBeer(beerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Beer " + beerDTO.getName()
                + " was created.");
    }

    @GetMapping
    public ResponseEntity<List<BeerDTO>> findAll() {
        return ResponseEntity.ok(this.beerService.findAll());
    }


    @PutMapping
    public ResponseEntity<Void> updateBeer(@RequestBody BeerRequestUpdateDTO beerRequestUpdateDTO) {
        beerService.updateBeer(beerRequestUpdateDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteBeer(@PathVariable("name") String name) {
        this.beerService.deleteBeer(name);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Beer " + name + " has been deleted.");
    }
}
