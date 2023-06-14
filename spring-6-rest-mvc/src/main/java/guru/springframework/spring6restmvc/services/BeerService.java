package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    List<Beer> listBeers();

    Beer getById(UUID beerId);

    Beer saveNewBeer(Beer beer);

    void updateById(UUID beerId, Beer beer);

    void deleteById(UUID beerId);

    void patchById(UUID beerId, Beer beer);
}
