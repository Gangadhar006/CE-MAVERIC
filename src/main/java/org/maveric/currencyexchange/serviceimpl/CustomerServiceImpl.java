package org.maveric.currencyexchange.serviceimpl;

import org.maveric.currencyexchange.dtos.CustomerDto;
import org.maveric.currencyexchange.entity.Customer;
import org.maveric.currencyexchange.enums.GenderType;
import org.maveric.currencyexchange.exception.CustomerNotFoundException;
import org.maveric.currencyexchange.repository.ICustomerRepository;
import org.maveric.currencyexchange.service.ICustomerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {
    public static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private ModelMapper mapper;
    private ICustomerRepository customerRepo;

    public CustomerServiceImpl(ModelMapper mapper, ICustomerRepository customerRepo) {
        this.mapper = mapper;
        this.customerRepo = customerRepo;
    }

    @Override
    @Transactional
    public CustomerDto createCustomer(CustomerDto customerDto) {
        logger.info("Creating Customer: {}", customerDto.getFirstName());

        Customer customer = mapper.map(customerDto, Customer.class);
        customer = customerRepo.save(customer);
        customerDto = mapper.map(customer, CustomerDto.class);

        logger.info("Created Customer Successfully: {}", customerDto.getFirstName());

        return customerDto;
    }

    @Override
    @Transactional
    public CustomerDto updateCustomer(Long customerId, CustomerDto customerDto) {
        logger.info("Updating Customer with CUSTOMER-ID: {}", customerId);

        Customer customer = customerRepo.findById(customerId).orElseThrow(
                () -> {
                    logger.error("Customer Not Found With ID: {}", customerId);
                    return new CustomerNotFoundException("Customer Not Found With ID: " + customerId);
                }
        );
        updateFirstName(customer, customerDto.getFirstName());
        updateLastName(customer, customerDto.getLastName());
        updateDobAndAge(customer, customerDto.getDob());
        updateEmail(customer, customerDto.getEmail());
        updateGender(customer, customerDto.getGender());
        updatePhone(customer, customerDto.getPhone());
        customerDto = mapper.map(customer, CustomerDto.class);
        logger.info(" Successfully updated Customer with CUSTOMER-ID: {}", customerId);
        return customerDto;
    }

    @Override
    public List<CustomerDto> findAllCustomers() {
        logger.info(" Fetching All Customers");
        List<Customer> customers = customerRepo.findAll();
        return customers.stream()
                .map(customer -> mapper.map(customer, CustomerDto.class))
                .toList();
    }

    @Override
    @Transactional
    public String deleteCustomer(Long customerId) {
        logger.info(" Deleting Customer With CUSTOMER-ID: {}", customerId);

        Customer customer = customerRepo.findById(customerId).orElseThrow(
                () -> {
                    logger.error("Customer Not Found With ID: {}", customerId);
                    return new CustomerNotFoundException("Customer Not Found With ID: " + customerId);
                }
        );
        customerRepo.delete(customer);
        logger.info(" Successfully Deleted Customer With CUSTOMER-ID: {}", customerId);
        return "Customer Deleted Successfully";
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