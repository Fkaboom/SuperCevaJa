package br.org.fundatec.SuperCevaJa.controller;

import br.org.fundatec.SuperCevaJa.dto.beer.type.BeerTypeDTO;
import br.org.fundatec.SuperCevaJa.service.BeerTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class BeerTypeControllerTest {

    @Mock
    private BeerTypeService beerTypeService;

    @InjectMocks
    private BeerTypeController beerTypeController;

    @BeforeEach
    public void setup() {
        // Configurações antes de cada teste, se necessário
    }

    @Test
    public void testCreateBeerSuccess() {
        // Crie um UserRequestCreateDTO para simular o corpo da requisição

        BeerTypeDTO beerTypeDTO = new BeerTypeDTO();
        beerTypeDTO.setName("Brahma");
        beerTypeDTO.setPrice(BigDecimal.valueOf(2));

        // Simule o comportamento do BeerService para o método createBeer
        doNothing().when(beerTypeService).createBeerType(beerTypeDTO);

        // Chame o método da controller que você quer testar
        ResponseEntity<String> responseEntity = beerTypeController.createBeerType(beerTypeDTO);

        // Verifique se a resposta HTTP é a esperada
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Beer " + beerTypeDTO.getName()
                + " was created.", responseEntity.getBody());

        // Verifique se o método do BeerTypeService foi chamado com os parâmetros corretos
        verify(beerTypeService, times(1)).createBeerType(beerTypeDTO);
    }

    @Test
    public void TestCreateRepeatedBeerFails() {
        // Crie um UserRequestCreateDTO para simular o corpo da requisição

        BeerTypeDTO beerTypeDTO = new BeerTypeDTO();
        beerTypeDTO.setName("Brahma");
        beerTypeDTO.setPrice(BigDecimal.valueOf(2));
        // Simule o comportamento do beerTypeService para o método createBeer
        doNothing().when(beerTypeService).createBeerType(beerTypeDTO);

        BeerTypeDTO repetedBeer = new BeerTypeDTO();
        repetedBeer.setName("Brahma");
        repetedBeer.setPrice(BigDecimal.valueOf(2));

        doNothing().when(beerTypeService).createBeerType(repetedBeer);
        ResponseEntity<String> responseEntity = beerTypeController.createBeerType(beerTypeDTO);

        // Verifique se a resposta HTTP é a esperada
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Beer " + beerTypeDTO.getName()
                + " was created.", responseEntity.getBody());

        // Verifique se o método do UserService foi chamado com os parâmetros corretos
        verify(beerTypeService, times(1)).createBeerType(beerTypeDTO);
    }

}
