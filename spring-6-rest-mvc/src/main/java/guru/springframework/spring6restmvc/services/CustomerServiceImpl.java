package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();


        Customer customer1 = Customer.builder()
                .customerName("John")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .customerName("Mary")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .customerName("Bob")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);

        log.debug("Loaded Customers: " + customerMap.size());
    }


    @Override
    public List<Customer> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getById(UUID customerId) {
        log.debug("Getting Customer by Id: " + customerId + " from CustomerServiceImpl");
        return customerMap.get(customerId);
    }

    @Override
    public Customer saveNewCustomer(Customer customer) {

        Customer savedCustomer = Customer.builder()
                .customerName(customer.getCustomerName())
                .id(UUID.randomUUID())
                .version(customer.getVersion())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }

    @Override
    public void updateById(UUID customerId, Customer customer) {
        Customer existing = customerMap.get(customerId);
        existing.setCustomerName(customer.getCustomerName());
        log.debug("Customer Name updated to: " + customer.getCustomerName());
        existing.setVersion(customer.getVersion());
        log.debug("Version updated to: " + customer.getVersion());
        existing.setLastModifiedDate(LocalDateTime.now());
        customerMap.put(customerId, existing);
    }

    @Override
    public void deleteById(UUID customerId) {
        log.debug("Deleting Customer: " + customerId + " from CustomerServiceImpl");
        customerMap.remove(customerId);
    }

    @Override
    public void patchById(UUID customerId, Customer customer) {

        Customer existing = customerMap.get(customerId);
        if (StringUtils.hasText(customer.getCustomerName())) {
            existing.setCustomerName(customer.getCustomerName());
            log.debug("Customer Name updated to: " + customer.getCustomerName());
        }
        if (customer.getVersion() != null) {
            existing.setVersion(customer.getVersion());
            log.debug("Version updated to: " + customer.getVersion());
        }
        existing.setLastModifiedDate(LocalDateTime.now());
        customerMap.put(customerId, existing);

    }
}
