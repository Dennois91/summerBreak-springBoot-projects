package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<BeerDTO> listBeers();

    Optional<BeerDTO> getById(UUID beerId);

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    Optional<BeerDTO> updateById(UUID beerId, BeerDTO beerDTO);

    boolean deleteById(UUID beerId);

    Optional<BeerDTO> patchById(UUID beerId, BeerDTO beerDTO);
}
