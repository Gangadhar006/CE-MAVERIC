package org.maveric.currencyexchange.serviceimpl;

import org.maveric.currencyexchange.dtos.CustomerDto;
import org.maveric.currencyexchange.entity.Customer;
import org.maveric.currencyexchange.enums.GenderType;
import org.maveric.currencyexchange.repository.ICustomerRepository;
import org.maveric.currencyexchange.service.ICustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

        //throw CustomerNotFoundException here

        Optional<Customer> customer = customerRepo.findById(customerId);

        if (!Objects.isNull(customerDto) && customer.isPresent()) {
            Customer customerCopy = customer.get();
            updateFirstName(customerCopy, customerDto.getFirstName());
            updateLastName(customerCopy, customerDto.getLastName());
            updateDobAndAge(customerCopy, customerDto.getDob());
            updateEmail(customerCopy, customerDto.getEmail());
            updateGender(customerCopy, customerDto.getGender());
            updatePhone(customerCopy, customerDto.getPhone());
            customerDto=mapper.map(customerCopy, CustomerDto.class);
        }
        return customerDto;
    }

    @Override
    public List<CustomerDto> findAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        return customers.stream()
                .map(customer -> mapper.map(customer, CustomerDto.class))
                .toList();
    }

    @Override
    @Transactional
    public String deleteCustomer(Long id) {

        //throw CustomerNotFoundException here

        Optional<Customer> customer = customerRepo.findById(id);
        if (customer.isPresent())
            customerRepo.delete(customer.get());
        return "Customer deleted Successfully";
    }

    private void updateFirstName(Customer customer, String firstName) {
        if (firstName != null && !firstName.isBlank()) {
            customer.setFirstName(firstName);
        }
    }

    private void updateLastName(Customer customer, String lastName) {
        if (lastName != null && !lastName.isBlank()) {
            customer.setLastName(lastName);
        }
    }

    private void updateDobAndAge(Customer customer, LocalDate dob) {
        if (dob != null) {
            customer.setDob(dob);
            LocalDate currentDate = LocalDate.now();
            customer.setAge(Period.between(dob, currentDate).getYears());
        }
    }

    private void updateEmail(Customer customer, String email) {
        if (email != null && !email.isBlank()) {
            customer.setEmail(email);
        }
    }

    private void updateGender(Customer customer, GenderType gender) {
        if (gender != null) {
            customer.setGender(gender);
        }
    }

    private void updatePhone(Customer customer, String phone) {
        if (phone != null && !phone.isBlank()) {
            customer.setPhone(phone);
        }
    }
}