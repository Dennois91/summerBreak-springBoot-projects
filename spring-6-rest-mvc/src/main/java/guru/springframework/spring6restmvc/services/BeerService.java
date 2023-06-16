package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<BeerDTO> listBeers();

    Optional<BeerDTO> getById(UUID beerId);

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    void updateById(UUID beerId, BeerDTO beerDTO);

    void deleteById(UUID beerId);

    void patchById(UUID beerId, BeerDTO beerDTO);
}
