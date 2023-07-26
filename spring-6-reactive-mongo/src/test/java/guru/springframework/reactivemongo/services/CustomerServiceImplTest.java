package guru.springframework.reactivemongo.services;

import guru.springframework.reactivemongo.domain.Customer;
import guru.springframework.reactivemongo.mappers.CustomerMapper;
import guru.springframework.reactivemongo.mappers.CustomerMapperImpl;
import guru.springframework.reactivemongo.model.CustomerDTO;
import guru.springframework.reactivemongo.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public
class CustomerServiceImplTest {

    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    CustomerRepository customerRepository;
    CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        customerDTO = customerMapper.customerToCustomerDto(getTestCustomer());
    }

    @Test
    void findFirstByCustomerName() {
        CustomerDTO customerDTO1 = getSavedCustomerDto();

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        Mono<CustomerDTO> foundDto = customerService.findFirstByCustomerName(customerDTO1.getCustomerName());
        foundDto.subscribe(customerDTO2 -> {
            System.out.println(customerDTO2.toString());
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void listCustomers() {
        Flux<CustomerDTO> flux = customerService.listCustomers();

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        flux.subscribe(list -> {
            System.out.println(list.toString());
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void saveCustomer() {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Mono<CustomerDTO> savedMono = customerService.saveCustomer(Mono.just(customerDTO));

        savedMono.subscribe(savedDto -> {
            System.out.println(savedDto.getId());
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void getById() {
        CustomerDTO customerDTO1 = getSavedCustomerDto();
        AtomicReference<CustomerDTO> response = new AtomicReference<>();

        customerService.getById(customerDTO1.getId())
                .doOnNext(response::set)
                .subscribe();

        await().untilAsserted(() -> assertThat(response.get()).isEqualTo(customerDTO1));
    }

    @Test
    void updateCustomer() {
        CustomerDTO customerDTO1 = getSavedCustomerDto();
        final String newName = "New Name";
        customerDTO1.setCustomerName(newName);
        AtomicReference<CustomerDTO> response = new AtomicReference<>();

        customerService.updateCustomer(customerDTO1.getId(), customerDTO1)
                .doOnNext(response::set)
                .subscribe();

        await().untilAsserted(() -> assertEquals(newName, response.get().getCustomerName()));
    }

    @Test
    void deleteCustomerById() {
        CustomerDTO customerDTO1 = getSavedCustomerDto();
        AtomicBoolean deleteComplete = new AtomicBoolean(false);
        customerService.deleteCustomerById(customerDTO1.getId())
                .doOnTerminate(() -> deleteComplete.set(true))
                .subscribe();

        await().untilTrue(deleteComplete);

        AtomicReference<CustomerDTO> response = new AtomicReference<>();

        customerService.getById(customerDTO1.getId())
                .doOnNext(response::set)
                .subscribe();

        await().untilAsserted(() -> assertThat(response.get()).isNull());
    }

    public CustomerDTO getSavedCustomerDto() {
        return customerService.saveCustomer(Mono.just(getTestCustomerDto())).block();
    }

    public static CustomerDTO getTestCustomerDto() {
        return new CustomerMapperImpl().customerToCustomerDto(getTestCustomer());
    }

    public static Customer getTestCustomer() {
        return Customer.builder()
                .customerName("Test Customer").build();
    }

}