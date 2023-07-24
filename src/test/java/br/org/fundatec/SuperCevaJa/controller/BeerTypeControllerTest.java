package br.org.fundatec.SuperCevaJa.controller;

import br.org.fundatec.SuperCevaJa.dto.beer.type.BeerTypeDTO;
import br.org.fundatec.SuperCevaJa.dto.user.UserRequestCreateDTO;
import br.org.fundatec.SuperCevaJa.service.BeerTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BeerTypeControllerTest {

    @Mock
    private BeerTypeService beerTypeService;

    @InjectMocks
    private BeerTypeController beerTypeController;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testCreateBeerSuccess() {
        BeerTypeDTO beerTypeDTO = new BeerTypeDTO();
        beerTypeDTO.setName("Brahma");
        beerTypeDTO.setPrice(BigDecimal.valueOf(2));

        doNothing().when(beerTypeService).createBeerType(beerTypeDTO);

        ResponseEntity<String> responseEntity = beerTypeController.createBeerType(beerTypeDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Beer " + beerTypeDTO.getName() + " was created.", responseEntity.getBody());

        verify(beerTypeService, times(1)).createBeerType(beerTypeDTO);
    }


    @Test
    public void testCreateRepeatedBeerFails() {

        BeerTypeDTO beerTypeDTO = new BeerTypeDTO();
        beerTypeDTO.setName("Brahma");
        beerTypeDTO.setPrice(BigDecimal.valueOf(2));

        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Cannot add repeated Beer Type "  + beerTypeDTO.getName())).when(beerTypeService).createBeerType(beerTypeDTO);

        ResponseEntity<String> responseEntity = beerTypeController.createBeerType(beerTypeDTO);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        String expectedErrorMessage = "Cannot add repeated Beer Type " + beerTypeDTO.getName();
        String actualErrorMessage = responseEntity.getBody().replace("\"", "");
        assertEquals(HttpStatus.BAD_REQUEST + " " + expectedErrorMessage, actualErrorMessage);

        verify(beerTypeService, times(1)).createBeerType(beerTypeDTO);
    }

    @Test
    public void testDeleteBeerType_Success() {
        // Defina um nome de tipo de cerveja válido para exclusão
        String beerTypeNameToDelete = "IPA";

        // Simule o comportamento do BeerTypeService para o método deleteBeerType
        // Considerando que a exclusão é bem-sucedida
        doNothing().when(beerTypeService).deleteBeerType(beerTypeNameToDelete);

        // Chame o método da controller que você quer testar
        ResponseEntity<String> responseEntity = beerTypeController.deleteBeerType(beerTypeNameToDelete);

        // Verifique se a resposta HTTP é a esperada (202 - Accepted)
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals("Beer " + beerTypeNameToDelete + " has been deleted.", responseEntity.getBody());

        // Verifique se o método do BeerTypeService foi chamado com o nome correto
        verify(beerTypeService, times(1)).deleteBeerType(beerTypeNameToDelete);
    }

    @Test
    public void testDeleteBeerType_Failure() {
        // Defina um nome de tipo de cerveja inválido para exclusão
        String nonExistingBeerTypeName = "NonExistingType";

        // Simule o comportamento do BeerTypeService para o método deleteBeerType
        // Considerando que a exclusão lança uma exceção
        doThrow(new IllegalArgumentException("Beer Type not found with name " + nonExistingBeerTypeName))
                .when(beerTypeService).deleteBeerType(nonExistingBeerTypeName);

        // Chame o método da controller que você quer testar
        ResponseEntity<String> responseEntity = beerTypeController.deleteBeerType(nonExistingBeerTypeName);

        // Verifique se a resposta HTTP é a esperada (400 - Bad Request)
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Beer Type not found with name " + nonExistingBeerTypeName, responseEntity.getBody());

        // Verifique se o método do BeerTypeService foi chamado com o nome correto
        verify(beerTypeService, times(1)).deleteBeerType(nonExistingBeerTypeName);
    }

    @Test
    public void testFindAll() {
        // Crie uma lista de BeerTypeDTO para simular o retorno do serviço
        List<BeerTypeDTO> beerTypes = new ArrayList<>();
        BeerTypeDTO beerTypeDTO = new BeerTypeDTO();
        beerTypeDTO.setName("Brahma");
        beerTypeDTO.setPrice(BigDecimal.valueOf(2));

        BeerTypeDTO beerTypeDTO2 = new BeerTypeDTO();
        beerTypeDTO2.setName("Itaipava");
        beerTypeDTO2.setPrice(BigDecimal.valueOf(2));
        beerTypes.add(beerTypeDTO);
        beerTypes.add(beerTypeDTO2);
        // Simule o comportamento do BeerTypeService para o método findAll
        when(beerTypeService.findAll()).thenReturn(beerTypes);

        // Chame o método da controller que você quer testar
        ResponseEntity<List<BeerTypeDTO>> responseEntity = beerTypeController.findAll();

        // Verifique se a resposta HTTP é a esperada (200 - OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verifique se o método do BeerTypeService foi chamado
        verify(beerTypeService, times(1)).findAll();

        // Verifique se a lista de BeerTypeDTO retornada na resposta é a mesma do serviço
        List<BeerTypeDTO> returnedBeerTypes = responseEntity.getBody();
        assertEquals(beerTypes.size(), returnedBeerTypes.size());
        assertEquals(beerTypes.get(0).getName(), returnedBeerTypes.get(0).getName());
        assertEquals(beerTypes.get(0).getPrice(), returnedBeerTypes.get(0).getPrice());
        assertEquals(beerTypes.get(1).getName(), returnedBeerTypes.get(1).getName());
        assertEquals(beerTypes.get(1).getPrice(), returnedBeerTypes.get(1).getPrice());
    }
}


