package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .stream().map(beerMapper::beertoBeerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> getById(UUID beerId) {
        return Optional.ofNullable(beerMapper.beertoBeerDto(beerRepository.findById(beerId)
                .orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        return null;
    }

    @Override
    public void updateById(UUID beerId, BeerDTO beerDTO) {

    }

    @Override
    public void deleteById(UUID beerId) {

    }

    @Override
    public void patchById(UUID beerId, BeerDTO beerDTO) {

    }
}
