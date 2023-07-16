package br.org.fundatec.SuperCevaJa.service;

import br.org.fundatec.SuperCevaJa.dto.beer.type.BeerTypeDTO;
import br.org.fundatec.SuperCevaJa.dto.beer.type.BeerTypeRequestUpdateDTO;
import br.org.fundatec.SuperCevaJa.model.BeerTypeModel;
import br.org.fundatec.SuperCevaJa.model.UserModel;
import br.org.fundatec.SuperCevaJa.repository.BeerTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BeerTypeService {
    private BeerTypeRepository beerTypeRepository;

    public BeerTypeService(BeerTypeRepository beerTypeRepository) {
        this.beerTypeRepository = beerTypeRepository;
    }

    public void createBeerType(BeerTypeDTO beerTypeDTO) {
        BeerTypeModel beerTypeModel = convertToModel(beerTypeDTO);
        Optional<BeerTypeModel> existingBeerType = Optional.ofNullable(beerTypeRepository.findByName(beerTypeModel.getName()));

        boolean isPresent = existingBeerType.isPresent();

        if (isPresent) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot add repeated Beer Type " + beerTypeModel.getName());
        }

        beerTypeRepository.save(beerTypeModel);
    }

    public List<BeerTypeDTO> findAll() {
        List<BeerTypeModel> beerTypeModel = beerTypeRepository.findAll();

        List<BeerTypeDTO> activeBeerType = beerTypeModel.stream()
                .filter(beer -> beer.getDeletedAt() == null).map(this::convertToDTO)
                .collect(Collectors.toList());

        return activeBeerType;
    }

    public BeerTypeModel findByName(String name) {
        Optional<BeerTypeModel> existingBeerType = Optional.ofNullable(beerTypeRepository.findByName(name));

        boolean isEmpty = existingBeerType.isEmpty();

        if (isEmpty) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Beer type not found with name " + name);
        }

        return existingBeerType.get();

    }

    public BeerTypeModel findByIdModel(Long id) {
        Optional<BeerTypeModel> beerTypeModel = beerTypeRepository.findById(id);

        boolean isEmpty = beerTypeModel.isEmpty();
        boolean isDeleted = beerTypeModel.get().getDeletedAt() != null;


        if (isDeleted || isEmpty) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Beer not found with ID " + id);
        }

        return beerTypeModel.get();
    }

    public void updateBeerType(BeerTypeRequestUpdateDTO beerTypeRequestUpdateDTO) {
        Long userId = beerTypeRequestUpdateDTO.getId();
        Optional<BeerTypeModel> foundBeerType = Optional.ofNullable(findByIdModel(userId));
        BeerTypeModel updatedBeerType = foundBeerType.get();

        updatedBeerType.setPrice(beerTypeRequestUpdateDTO.getPrice());

        this.beerTypeRepository.save(updatedBeerType);
    }

    public void deleteBeerType(String name) {
        Optional<BeerTypeModel> existingBeerType = Optional.ofNullable(beerTypeRepository.findByName(name));

        boolean isEmpty = existingBeerType.isEmpty();

        if (isEmpty) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Beer Type not found with name " + name);
        }

        BeerTypeModel deletedBeerType = existingBeerType.get();
        deletedBeerType.setDeletedAt(LocalDateTime.now());
        this.beerTypeRepository.save(deletedBeerType);

    }

    private BeerTypeModel convertToModel(BeerTypeDTO beerTypeDTO) {
        BeerTypeModel beerTypeModel = new BeerTypeModel();
        beerTypeModel.setName(beerTypeDTO.getName());
        beerTypeModel.setPrice(beerTypeDTO.getPrice());

        return beerTypeModel;
    }

    private BeerTypeDTO convertToDTO(BeerTypeModel beerTypeModel) {
        BeerTypeDTO beerTypeDTO = new BeerTypeDTO();
        beerTypeDTO.setName(beerTypeModel.getName());
        beerTypeDTO.setPrice(beerTypeModel.getPrice());

        return beerTypeDTO;
    }

}
