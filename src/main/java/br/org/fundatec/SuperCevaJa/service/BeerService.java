package br.org.fundatec.SuperCevaJa.service;

import br.org.fundatec.SuperCevaJa.dto.beer.BeerDTO;
import br.org.fundatec.SuperCevaJa.dto.beer.BeerRequestUpdateDTO;
import br.org.fundatec.SuperCevaJa.model.BeerModel;
import br.org.fundatec.SuperCevaJa.repository.BeerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BeerService {
    private BeerRepository beerRepository;

    public BeerService(BeerRepository beerRepository){
        this.beerRepository = beerRepository;
    }
    public void createBeer(BeerDTO beerDTO) {
        BeerModel beerModel = convertToModel(beerDTO);
        Optional<BeerModel> existingBeer = Optional.ofNullable(beerRepository.findByName(beerModel.getName()));

        if (existingBeer.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot add repeated Beer Type: " + beerModel.getName());
        }
        beerRepository.save(beerModel);
    }

    public List<BeerDTO> findAll() {
        List <BeerModel> beerModel = beerRepository.findAll();

        List<BeerDTO> activeBeer = beerModel.stream()
                .filter(beer -> beer.getDeletedAt() == null).map(this::convertToDTO)
                .collect(Collectors.toList());

        return activeBeer;
    }

    public void updateBeer(BeerRequestUpdateDTO beerRequestUpdateDTO){
        Optional<BeerModel> foundBeer = Optional.ofNullable(findByIdModel(beerRequestUpdateDTO.getId()));
        BeerModel updatedBeer = foundBeer.get();

        updatedBeer.setPrice(beerRequestUpdateDTO.getPrice());

        this.beerRepository.save(updatedBeer);
    }

    public void deleteBeer(String name){
        Optional<BeerModel> existingBeer = Optional.ofNullable(beerRepository.findByName(name));
        if (!existingBeer.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Beer not found with login: " + name);
        }
        BeerModel deletedBeer = existingBeer.get();
        deletedBeer.setDeletedAt(LocalDateTime.now());
        this.beerRepository.save(deletedBeer);

    }

    public BeerModel findByIdModel(Long id) {
        Optional<BeerModel> beerModel = beerRepository.findById(id);

        if (beerModel.isPresent() && beerModel.get().getDeletedAt() == null){
            return beerModel.get();
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Beer not found with ID: " + id);
        }
    }
    private BeerModel convertToModel(BeerDTO beerDTO) {
        BeerModel beerModel = new BeerModel();
        beerModel.setId(beerDTO.getId());
        beerModel.setName(beerDTO.getName());
        beerModel.setPrice(beerDTO.getPrice());

        return beerModel;
    }

    private BeerDTO convertToDTO(BeerModel beerModel) {
        BeerDTO beerDTO = new BeerDTO();
        beerDTO.setId(beerModel.getId());
        beerDTO.setName(beerModel.getName());
        beerDTO.setPrice(beerModel.getPrice());

        return beerDTO;
    }

}
