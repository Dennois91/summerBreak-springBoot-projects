package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerClientImplTest {
    @Autowired
    BeerClientImpl beerClient;

    @Test
    void deleteBeerTest(){
        BeerDTO newDto = BeerDTO.builder().beerName("Mango Bobs 2")
                .price(new BigDecimal("10.99"))
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("12345")
                .build();

        BeerDTO beerDTO = beerClient.createBeer(newDto);
        beerClient.deleteBeer(beerDTO.getId());

        assertThrows(HttpClientErrorException.class, () -> {
            beerClient.getBeerById(beerDTO.getId());
        });
    }

    @Test
    void updateBeerTest() {
        BeerDTO newDto = BeerDTO.builder().beerName("Mango Bobs 2")
                .price(new BigDecimal("10.99"))
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("12345")
                .build();

        BeerDTO beerDTO = beerClient.createBeer(newDto);

        final String newName = "Mango Bobs 3";
        beerDTO.setBeerName(newName);
        BeerDTO updatedBeer = beerClient.updateBeer(beerDTO);

        assertEquals(newName, updatedBeer.getBeerName());
    }

    @Test
    void createBeerTest() {

        BeerDTO newDto = BeerDTO.builder()
                .beerName("Mango Bobs")
                .price(new BigDecimal("10.99"))
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("12345")
                .build();

        BeerDTO savedDto = beerClient.createBeer(newDto);
        assertNotNull(savedDto);
    }

    @Test
    void getBeerByIdTest() {
        Page<BeerDTO> beerDTOs = beerClient.listBeers();
        BeerDTO dto = beerDTOs.getContent().get(0);
        BeerDTO byId = beerClient.getBeerById(dto.getId());
        assertNotNull(byId);
    }

    @Test
    void listBeersTest() {

        beerClient.listBeers();

    }

    @Test
    void listNoBeersTest() {

        beerClient.listBeers(null, BeerStyle.ALE, null, null, 2000);

    }
}