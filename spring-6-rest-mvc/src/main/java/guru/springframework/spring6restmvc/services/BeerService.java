package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);

    Optional<BeerDTO> getById(UUID beerId);

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    Optional<BeerDTO> updateById(UUID beerId, BeerDTO beerDTO);

    boolean deleteById(UUID beerId);

    Optional<BeerDTO> patchById(UUID beerId, BeerDTO beerDTO);
}
