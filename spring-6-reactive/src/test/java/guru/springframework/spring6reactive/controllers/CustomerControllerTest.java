package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.domain.Customer;
import guru.springframework.spring6reactive.model.CustomerDTO;
import guru.springframework.spring6reactive.repositories.CustomerRepositoryTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class CustomerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(1)
    void getCustomerByIdTest() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get()
                .uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Customer.class);
    }

    @Test
    void getCustomerByIdNotFoundTest() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get()
                .uri(CustomerController.CUSTOMER_PATH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(4)
    void listCustomersTest() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get()
                .uri(CustomerController.CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.size()").isEqualTo(2);
    }

    @Test
    void createNewCustomerTest() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .post()
                .uri(CustomerController.CUSTOMER_PATH)
                .body(Mono.just(CustomerRepositoryTest.getTestCustomer()), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8090/api/v2/customer/3");
    }


    @Test
    void createNewCustomerBadDataTest() {
        Customer testCustomer = CustomerRepositoryTest.getTestCustomer();
        testCustomer.setCustomerName("");

        webTestClient
                .mutateWith(mockOAuth2Login())
                .post()
                .uri(CustomerController.CUSTOMER_PATH)
                .body(Mono.just(testCustomer), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }


    @Test
    @Order(2)
    void updateCustomerByIdTest() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(CustomerRepositoryTest.getTestCustomer()), CustomerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void updateCustomerByIdBadDataTest() {
        Customer testCustomer = CustomerRepositoryTest.getTestCustomer();
        testCustomer.setCustomerName("");

        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(testCustomer), CustomerDTO.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateCustomerByIdNotFoundTest() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(CustomerController.CUSTOMER_PATH_ID, 999)
                .body(Mono.just(CustomerRepositoryTest.getTestCustomer()), CustomerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void patchCustomerByIdTest() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch()
                .uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(CustomerRepositoryTest.getTestCustomer()), CustomerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void patchCustomerByIdBadDataTest() {
        Customer testCustomer = CustomerRepositoryTest.getTestCustomer();
        testCustomer.setCustomerName("");

        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch()
                .uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(testCustomer), CustomerDTO.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void patchCustomerByIdNotFoundTest() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch()
                .uri(CustomerController.CUSTOMER_PATH_ID, 999)
                .body(Mono.just(CustomerRepositoryTest.getTestCustomer()), CustomerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }


    @Test
    @Order(999)
    void deleteCustomerByIdTest() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete()
                .uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deleteCustomerByIdNotFoundTest() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete()
                .uri(CustomerController.CUSTOMER_PATH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }
}