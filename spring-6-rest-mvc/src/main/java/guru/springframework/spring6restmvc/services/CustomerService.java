package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;
import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> listCustomers();

    Customer getById(UUID customerId);

    Customer saveNewCustomer(Customer customer);

    void updateById(UUID customerId, Customer customer);

    void deleteById(UUID customerId);

    void patchById(UUID customerId, Customer customer);
}
