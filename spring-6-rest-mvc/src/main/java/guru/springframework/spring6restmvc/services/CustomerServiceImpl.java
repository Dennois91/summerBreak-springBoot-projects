package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();


        CustomerDTO customerDTO1 = CustomerDTO.builder()
                .customerName("John")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        CustomerDTO customerDTO2 = CustomerDTO.builder()
                .customerName("Mary")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        CustomerDTO customerDTO3 = CustomerDTO.builder()
                .customerName("Bob")
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(customerDTO1.getId(), customerDTO1);
        customerMap.put(customerDTO2.getId(), customerDTO2);
        customerMap.put(customerDTO3.getId(), customerDTO3);

        log.debug("Loaded Customers: " + customerMap.size());
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getById(UUID customerId) {
        log.debug("Getting Customer by Id: " + customerId + " from CustomerServiceImpl");
        return Optional.of(customerMap.get(customerId));
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) {

        CustomerDTO savedCustomerDTO = CustomerDTO.builder()
                .customerName(customerDTO.getCustomerName())
                .id(UUID.randomUUID())
                .version(customerDTO.getVersion())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(savedCustomerDTO.getId(), savedCustomerDTO);
        return savedCustomerDTO;
    }

    @Override
    public void updateById(UUID customerId, CustomerDTO customerDTO) {
        CustomerDTO existing = customerMap.get(customerId);
        existing.setCustomerName(customerDTO.getCustomerName());
        log.debug("Customer Name updated to: " + customerDTO.getCustomerName());
        existing.setVersion(customerDTO.getVersion());
        log.debug("Version updated to: " + customerDTO.getVersion());
        existing.setLastModifiedDate(LocalDateTime.now());
        customerMap.put(customerId, existing);
    }

    @Override
    public void deleteById(UUID customerId) {
        log.debug("Deleting Customer: " + customerId + " from CustomerServiceImpl");
        customerMap.remove(customerId);
    }

    @Override
    public void patchById(UUID customerId, CustomerDTO customerDTO) {

        CustomerDTO existing = customerMap.get(customerId);
        if (StringUtils.hasText(customerDTO.getCustomerName())) {
            existing.setCustomerName(customerDTO.getCustomerName());
            log.debug("Customer Name updated to: " + customerDTO.getCustomerName());
        }
        if (customerDTO.getVersion() != null) {
            existing.setVersion(customerDTO.getVersion());
            log.debug("Version updated to: " + customerDTO.getVersion());
        }
        existing.setLastModifiedDate(LocalDateTime.now());
        customerMap.put(customerId, existing);

    }
}
