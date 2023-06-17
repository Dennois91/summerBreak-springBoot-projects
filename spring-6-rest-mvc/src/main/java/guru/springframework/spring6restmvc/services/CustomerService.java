package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> listCustomers();

    Optional<CustomerDTO> getById(UUID customerId);

    CustomerDTO saveNewCustomer(CustomerDTO customerDTO);

    Optional<CustomerDTO> updateById(UUID customerId, CustomerDTO customerDTO);

    boolean deleteById(UUID customerId);

    void patchById(UUID customerId, CustomerDTO customerDTO);
}
