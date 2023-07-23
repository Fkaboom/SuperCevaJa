package br.org.fundatec.SuperCevaJa.controller;

import br.org.fundatec.SuperCevaJa.dto.beer.type.BeerTypeDTO;
import br.org.fundatec.SuperCevaJa.dto.beer.type.BeerTypeRequestUpdateDTO;
import br.org.fundatec.SuperCevaJa.service.BeerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Felipe Brandão e João Gabriel C. da Cruz
 * Controller responsavel para lidar com as operações ligadas com cervejas
 */

@RestController
@RequestMapping("/cevaja/api/v1/beer/types")
@CrossOrigin(origins = "*")
public class BeerTypeController {
    private BeerTypeService beerTypeService;

    @Autowired
    public BeerTypeController(BeerTypeService beerTypeService) {
        this.beerTypeService = beerTypeService;
    }

    /***
     *
     * @param beerTypeDTO
     * @see BeerTypeDTO
     * @return beerTypeService.createBeerType
     * @throws HttpStatus.CREATED se funcionar
     * @throws HttpStatus.BAD_REQUEST se cerveja já existir ou tiver erro
     * Recebe corpo e cria cerveja
     */
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

    /**
     @return beerTypeService.beer
     Retorna lista de cerveja*/
    @GetMapping
    public ResponseEntity<List<BeerTypeDTO>> findAll() {
        return ResponseEntity.ok(this.beerTypeService.findAll());
    }


    /**
     * @param beerTypeRequestUpdateDTO
     * @see BeerTypeRequestUpdateDTO
     * @return beerTypeService.updateBeerType
     * @throws HttpStatus.NO_CONTENT se tiver cerveja
     * @throws HttpStatus.BAD_REQUEST se não tiver

     * Recebe o corpo e atualiza cerveja
     */
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

    /**
     * @param name
     Método que recebe nome de cerveja e deleta ela*/
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
