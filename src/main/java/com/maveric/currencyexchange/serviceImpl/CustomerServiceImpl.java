package com.maveric.currencyexchange.serviceImpl;

import com.maveric.currencyexchange.dtos.CustomerDto;
import com.maveric.currencyexchange.entity.Customer;
import com.maveric.currencyexchange.repository.ICustomerRepository;
import com.maveric.currencyexchange.service.ICustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements ICustomerService {
    private ModelMapper mapper;
    private ICustomerRepository customerRepo;

    public CustomerServiceImpl(ModelMapper mapper, ICustomerRepository customerRepo) {
        this.mapper = mapper;
        this.customerRepo = customerRepo;
    }

    @Override
    @Transactional
    public CustomerDto createCustomer(CustomerDto customerDto) {
        if (!Objects.isNull(customerDto)) {
            Customer customer = mapper.map(customerDto, Customer.class);
            customer = customerRepo.save(customer);
            customerDto = mapper.map(customer, CustomerDto.class);
            return customerDto;
        }
        return null;
    }

    @Override
    @Transactional
    public CustomerDto updateCustomer(Long customerId, CustomerDto customerDto) {
        if (!Objects.isNull(customerDto)) {
            Optional<Customer> customer = customerRepo.findById(customerId);

            Customer customerCopy = customer.get();

            if (customer.isPresent()) {
                if (customerDto.getFirstName() != null && !customerDto.getFirstName().isBlank())
                    customerCopy.setFirstName(customerDto.getFirstName());

                if (customerDto.getLastName() != null && !customerDto.getLastName().isBlank())
                    customerCopy.setLastName(customerDto.getLastName());

                if (customerDto.getDob() != null) {
                    customerCopy.setDob(customerDto.getDob());
                    LocalDate currentDate = LocalDate.now();
                    customerCopy.setAge(Period.between(customerDto.getDob(), currentDate).getYears());
                }

                if (customerDto.getEmail() != null && !customerDto.getEmail().isBlank())
                    customerCopy.setEmail(customerDto.getEmail());

                if (customerDto.getGender() != null)
                    customerCopy.setGender(customerDto.getGender());

                if (customerDto.getPhone() != null && !customerDto.getPhone().isBlank())
                    customerCopy.setPhone(customerDto.getPhone());
            }
            customerCopy = customerRepo.save(customerCopy);
            return mapper.map(customerCopy, CustomerDto.class);
        }
        return null;
    }

    @Override
    public List<CustomerDto> findAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        List<CustomerDto> customerDtos = customers.stream()
                .map(customer -> mapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());
        return customerDtos;
    }

    @Override
    @Transactional
    public CustomerDto deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepo.findById(id);
        if (customer.isPresent())
            customerRepo.delete(customer.get());
        return mapper.map(customer, CustomerDto.class);
    }
}
