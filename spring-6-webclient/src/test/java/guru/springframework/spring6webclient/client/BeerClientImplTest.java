package guru.springframework.spring6webclient.client;

import guru.springframework.spring6webclient.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClient client;

    @Test
    void listBeerTest() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeer().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void listBeerMapTest() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeerMap().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void getBeerJsonTest() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        // todo Learn deeper about jsonNode
        client.listBeersJsonNode().subscribe(jsonNode -> {
            System.out.println(jsonNode.toPrettyString());
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void getBeerDtoTest() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeerDtos().subscribe(dto -> {
            System.out.println(dto.getBeerName());
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void getBeerByIdTest() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeerDtos()
                .flatMap(dto -> client.getBeerById(dto.getId()))
                .subscribe(byIdDto -> {
                    System.out.println(byIdDto.getBeerName());
                    atomicBoolean.set(true);
                });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void getBeerByBeerStyleTest() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.getBeerByBeerStyle("Pale Ale")
                .subscribe(beerDTO -> {
                    System.out.println(beerDTO.toString());
                    atomicBoolean.set(true);

                });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void createBeerTest() {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        BeerDTO newDto = BeerDTO.builder()
                .beerName("Mango Bobs")
                .beerStyle("IPA")
                .price(new BigDecimal("10.99"))
                .quantityOnHand(500)
                .upc("234556")
                .build();

        client.createBeer(newDto)
                .subscribe(dto -> {
                    System.out.println(dto.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void updateBeerTest() {

        final String NAME = "New Name";

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeerDtos()
                .next()
                .doOnNext(beerDTO -> beerDTO.setBeerName(NAME))
                .flatMap(dto -> client.updateBeer(dto))
                .subscribe(byIdDto -> {
                    System.out.println(byIdDto.toString());
                    atomicBoolean.set(true);
                });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void pathBeerTest() {
        final String NAME = "Patched";

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeerDtos()
                .next()
                .doOnNext(beerDTO -> beerDTO.setBeerName(NAME))
                .flatMap(dto -> client.patchBeer(dto))
                .subscribe(byIdDto -> {
                    System.out.println(byIdDto.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);

    }

    @Test
    void deleteTest() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeerDtos()
                .next()
                .flatMap(dto -> client.deleteBeer(dto))
                .doOnSuccess(mt -> atomicBoolean.set(true))
                .subscribe();

        await().untilTrue(atomicBoolean);
    }

}
