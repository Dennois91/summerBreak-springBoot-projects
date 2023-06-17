package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;
;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;
    @Autowired
    BeerMapper beerMapper;

    @Test
    void listBeersTest() {
        List<BeerDTO> beerDTOS = beerController.listBeers();
        assertThat(beerDTOS.size()).isEqualTo(3);
    }

    @Transactional
    @Rollback
    @Test
    void emptyListTest() {
        beerRepository.deleteAll();
        List<BeerDTO> beerDTOS = beerController.listBeers();
        assertThat(beerDTOS.size()).isEqualTo(0);
    }

    @Test
    void getByIdTest() {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO dto = beerController.getById(beer.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void beerIdNotFoundTest() {
        assertThrows(NotFoundException.class, () -> {
            beerController.getById(UUID.randomUUID());
        });
    }

    @Transactional
    @Rollback
    @Test
    void saveBeerTest() {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("TestBeer")
                .build();

        ResponseEntity response = beerController.handlePost(beerDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(response.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = response.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();

    }


    @Test
    void updateNotFoundTest() {
        assertThrows(NotFoundException.class, () -> {
            beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Transactional
    @Rollback
    @Test
    void updateBeerTest() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beertoBeerDto(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String beerName = "UPDATED";
        beerDTO.setBeerName(beerName);

        ResponseEntity response = beerController.updateById(beer.getId(), beerDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);

    }
    @Test
    void patchBeerTest(){
    Beer beer = beerRepository.findAll().get(0);
    BeerDTO beerDTO = beerMapper.beertoBeerDto(beer);
    beerDTO.setId(null);
    beerDTO.setVersion(null);
    final String beerName = "PATCHED";
    beerDTO.setBeerName(beerName);

    ResponseEntity response = beerController.patchById(beer.getId(),beerDTO);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
    Beer patchedBeer = beerRepository.findById(beer.getId()).get();
    assertThat(patchedBeer.getBeerName()).isEqualTo(beerName);

    }

    @Transactional
    @Rollback
    @Test
    void deleteByIdFoundTest() {
        Beer beer = beerRepository.findAll().get(0);
        ResponseEntity response = beerController.deleteById(beer.getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(beer.getId()).isEmpty());
    }
    @Test
    void deleteByIdNotFoundTest(){
        assertThrows(NotFoundException.class, () -> {
            beerController.deleteById(UUID.randomUUID());
        });

    }
}