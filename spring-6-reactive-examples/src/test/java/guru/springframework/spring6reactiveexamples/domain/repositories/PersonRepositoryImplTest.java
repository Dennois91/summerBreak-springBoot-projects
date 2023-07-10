package guru.springframework.spring6reactiveexamples.domain.repositories;

import guru.springframework.spring6reactiveexamples.domain.Person;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryImplTest {

    PersonRepository personRepository = new PersonRepositoryImpl();

    @Test
    void monoByIdBlockTest() {
        Mono<Person> personMono = personRepository.getById(1);
        Person person = personMono.block();
    }

    @Test
    void getByIdSubscriberTest() {
        Mono<Person> personMono = personRepository.getById(1);
        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void mapOperationTest() {
        Mono<Person> personMono = personRepository.getById(1);

        personMono.map(person -> person.getFirstName()).subscribe(firstName -> {
            System.out.println(firstName);
        });
    }

    @Test
    void fluxBlockFirstTest() {
        Flux<Person> personFlux = personRepository.findAll();

        Person person = personFlux.blockFirst();

        System.out.println(person.toString());

    }

    @Test
    void fluxSubscriberTest() {
        Flux<Person> personFlux = personRepository.findAll();

        personFlux.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void fluxMapTest() {
        Flux<Person> personFlux = personRepository.findAll();

        personFlux.map(Person::getFirstName).subscribe(firstName -> System.out.println(firstName));
    }

    @Test
    void fluxToListTest() {
        Flux<Person> personFlux = personRepository.findAll();

        Mono<List<Person>> listMono = personFlux.collectList();

        listMono.subscribe(list -> {
            list.forEach(person -> System.out.println(person.getFirstName()));
        });
    }

    @Test
    void filterOnNameTest() {
        personRepository.findAll()
                .filter(person -> person.getFirstName().equals("Fiona"))
                .subscribe(person -> System.out.println(person.getFirstName()));
    }

    @Test
    void getByIdTest() {
        Mono<Person> testPerson = personRepository.getById(1);
        assertEquals(Boolean.TRUE, testPerson.hasElement().block());
    }

    @Test
    void getByIdStepVerifierTest() {
        Mono<Person> testPerson = personRepository.getById(1);

        StepVerifier.create(testPerson).expectNextCount(1).verifyComplete();

        testPerson.subscribe(person -> {
            System.out.println(person.getFirstName());
        });
    }


    @Test
    void getByIdNotFoundTest() {
        Mono<Person> testPerson = personRepository.getById(10);
        assertEquals(Boolean.FALSE, testPerson.hasElement().block());
    }

    @Test
    void getByIdNotFoundStepVerifierTest() {
        Mono<Person> testPerson = personRepository.getById(10);

        StepVerifier.create(testPerson).expectNextCount(0).verifyComplete();

        testPerson.subscribe(person -> {
            System.out.println(person.getFirstName());
        });
    }

    @Test
    void findPersonByIdNotFoundTest() {
        Flux<Person> personFlux = personRepository.findAll();

        final Integer id = 8;

        Mono<Person> personMono = personFlux.filter(person -> Objects.equals(person.getId(), id)).single()
                .doOnError(throwable -> {
                    System.out.println("Error occurred in flux");
                    System.out.println(throwable.toString());
                });

        personMono.subscribe(person -> {
            System.out.println(person.toString());
        }, throwable -> {
            System.out.println("Error occurred in the mono");
            System.out.println(throwable.toString());
        });
    }
}