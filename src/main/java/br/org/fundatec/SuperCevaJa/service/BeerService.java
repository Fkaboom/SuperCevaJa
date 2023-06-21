package br.org.fundatec.SuperCevaJa.service;

import br.org.fundatec.SuperCevaJa.model.BeerModel;
import br.org.fundatec.SuperCevaJa.model.UserModel;
import br.org.fundatec.SuperCevaJa.repository.BeerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BeerService {
    private BeerRepository beerRepository;

    public BeerModel createBeer(BeerModel beerModel) {
        //if ()

        BeerModel createBeerModel = beerRepository.save(beerModel);
        return createBeerModel;
    }

    public BeerModel findById(Long id) {
        Optional<BeerModel> beerModel = beerRepository.findById(id);
        if (beerModel.isPresent()){
            return beerModel.get();
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "beer not found with ID: " + id);
        }
    }

    public List<BeerModel> findAll() {return this.beerRepository.findAll();}
    
    public void deleteBeer(String name) {
        this.beerRepository.deleteByName(name);
    }

    public BeerModel updateBeer(BeerModel beerModel) {
        BeerModel updatedBeerModel = this.beerRepository.save(beerModel);
        return updatedBeerModel;
    }
}
