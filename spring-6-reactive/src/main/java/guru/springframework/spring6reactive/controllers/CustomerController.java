package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.model.BeerDTO;
import guru.springframework.spring6reactive.model.CustomerDTO;
import guru.springframework.spring6reactive.services.CustomerService;
import io.r2dbc.spi.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v2/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    private final CustomerService customerService;

    @GetMapping(CUSTOMER_PATH_ID)
    Mono<CustomerDTO> getCustomerById(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomerById(customerId);
    }

    @GetMapping(CUSTOMER_PATH)
    Flux<CustomerDTO> listCustomers() {
        return customerService.listCustomers();
    }

    @PostMapping(CUSTOMER_PATH)
    Mono<ResponseEntity<Void>> createNewCustomer(@Validated @RequestBody CustomerDTO customerDTO) {

        return customerService.saveNewCustomer(customerDTO)
                .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                                .fromHttpUrl("http://localhost:8090/" + CUSTOMER_PATH
                                        + "/" + savedDto.getId())
                                .build().toUri())
                        .build());
    }

    @PutMapping(CUSTOMER_PATH_ID)
    Mono<ResponseEntity<Void>> updateCustomerById(@PathVariable("customerId") Integer id,
                                                  @Validated @RequestBody CustomerDTO customerDTO) {

        return customerService.updateCustomer(id, customerDTO)
                .map(updatedDTO -> ResponseEntity.ok().build());

    }

    @PatchMapping(CUSTOMER_PATH_ID)
    Mono<ResponseEntity<Void>> patchCustomerById(@PathVariable("customerId") Integer id,
                                                 @Validated @RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(id, customerDTO)
                .map(patchedDTO -> ResponseEntity.ok().build());
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    Mono<ResponseEntity<Void>> deleteCustomerById(@PathVariable("customerId") Integer id) {
        return customerService.deleteCustomerById(id)
                .then(Mono.fromCallable(() -> ResponseEntity.noContent().build()));
    }
}
