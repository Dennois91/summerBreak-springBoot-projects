package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {
    @Autowired
    CustomerController customerController;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerMapper customerMapper;

    @Test
    void listCustomersTest() {
        List<CustomerDTO> customerDTOS = customerController.listCustomers();
        assertThat(customerDTOS.size()).isEqualTo(3);
    }

    @Transactional
    @Rollback
    @Test
    void emptyListTest() {
        customerRepository.deleteAll();
        List<CustomerDTO> beerDTOS = customerController.listCustomers();
        assertThat(beerDTOS.size()).isEqualTo(0);

    }

    @Test
    void getByIdTest() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO dto = customerController.getById(customer.getId());
        assertThat(dto).isNotNull();
    }

    @Test
    void customerIdNotFoundTest() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getById(UUID.randomUUID());
        });

    }

    @Transactional
    @Rollback
    @Test
    void saveCustomerTest() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("New Customer")
                .build();

        ResponseEntity response = customerController.handlePost(customerDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(response.getHeaders().getLocation()).isNotNull();

        String[] location = response.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(location[4]);

        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();
    }

    @Test
    void updateNotFoundTest() {
        assertThrows(NotFoundException.class, () -> {
            customerController.updateById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Transactional
    @Rollback
    @Test
    void updateCustomerTest() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setVersion(null);
        customerDTO.setId(null);
        String name = "UPDATED";
        customerDTO.setCustomerName(name);

        ResponseEntity response = customerController.updateById(customer.getId(), customerDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customer.getId()).get().getCustomerName())
                .isEqualTo(name);
    }

    @Test
    void patchCustomerTest() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);
        String name = "PATCHED";
        customerDTO.setCustomerName(name);

        ResponseEntity response = customerController.patchById(customer.getId(), customerDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customer.getId()).get().getCustomerName())
                .isEqualTo(name);

    }


    @Transactional
    @Rollback
    @Test
    void deleteByIdTest() {
        customerRepository.deleteAll();
        List<CustomerDTO> customerDTOS = customerController.listCustomers();
        assertThat(customerDTOS.size()).isEqualTo(0);
    }

    @Test
    void deleteByIdNotFoundTest() {
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteById(UUID.randomUUID());
        });
    }
}
/*
Complete Integration Tests and Implementations for  Update by Id, and Patch Customer
Use TDD
Also, implement JPA Patch operation for Beer entity - Very Similar to Update by Id
Include not found logic to provide HTTP 404 when not found
Refactor methods as needed
Assignment Review will be in a code review format, not live code
 */