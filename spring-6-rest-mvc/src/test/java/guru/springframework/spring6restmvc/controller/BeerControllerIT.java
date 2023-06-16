package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;

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

}