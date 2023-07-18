package guru.springframework.spring6reactive.services;

import guru.springframework.spring6reactive.mappers.CustomerMapper;
import guru.springframework.spring6reactive.model.CustomerDTO;
import guru.springframework.spring6reactive.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Flux<CustomerDTO> listCustomers() {
        return customerRepository.findAll()
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> saveNewCustomer(CustomerDTO customerDTO) {
        return customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO))
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> updateCustomer(Integer customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
                .map(foundCustomer -> {
                    foundCustomer.setCustomerName(customerDTO.getCustomerName());

                    return foundCustomer;
                }).flatMap(customerRepository::save)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<CustomerDTO> patchCustomer(Integer customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
                .map(patchCustomer -> {
                    if (StringUtils.hasText(customerDTO.getCustomerName())) {
                        patchCustomer.setCustomerName(customerDTO.getCustomerName());
                    }
                    return patchCustomer;
                }).flatMap(customerRepository::save)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Mono<Void> deleteCustomerById(Integer customerId) {
        return customerRepository.deleteById(customerId);
    }
}
