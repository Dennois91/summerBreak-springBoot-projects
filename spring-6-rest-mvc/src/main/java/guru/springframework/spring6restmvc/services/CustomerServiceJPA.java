package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll()
                .stream().map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getById(UUID customerId) {
        return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository.findById(customerId)
                .orElse(null)));
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public void updateById(UUID customerId, CustomerDTO customerDTO) {

    }

    @Override
    public void deleteById(UUID customerId) {

    }

    @Override
    public void patchById(UUID customerId, CustomerDTO customerDTO) {

    }
}
