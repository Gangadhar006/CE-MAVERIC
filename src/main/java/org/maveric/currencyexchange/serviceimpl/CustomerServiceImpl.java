package org.maveric.currencyexchange.serviceimpl;

import org.maveric.currencyexchange.entity.Customer;
import org.maveric.currencyexchange.exception.CustomerNotFoundException;
import org.maveric.currencyexchange.payload.CustomerRequest;
import org.maveric.currencyexchange.payload.CustomerResponse;
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

    @Transactional
    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        logger.info("Creating Customer");
        Customer customer = mapper.map(customerRequest, Customer.class);
        customer = customerRepo.save(customer);
        logger.info("Created Customer Successfully");
        return mapper.map(customer, CustomerResponse.class);
    }

    @Transactional
    @Override
    public CustomerResponse updateCustomer(long customerId, CustomerRequest customerRequest) {
        logger.info("Updating Customer");

        Customer customer = verifyCustomer(customerId);

        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customer.setGender(customerRequest.getGender());
        customer.setPhone(customerRequest.getPhone());
        updateDobAndAge(customer, customerRequest.getDob());

        logger.info(" Successfully updated Customer");

        return mapper.map(customer, CustomerResponse.class);
    }

    @Override
    public List<CustomerResponse> findAllCustomers() {
        logger.info(" Fetching All Customers");
        List<Customer> customers = customerRepo.findAll();
        return customers.stream()
                .map(customer -> mapper.map(customer, CustomerResponse.class))
                .toList();
    }

    @Override
    @Transactional
    public String deleteCustomer(long customerId) {
        logger.info(" Deleting Customer");
        Customer customer = verifyCustomer(customerId);

        customerRepo.delete(customer);
        logger.info(" Successfully Deleted Customer");
        return "Customer Deleted Successfully";
    }

    public Customer verifyCustomer(long customerId) {
        return customerRepo.findById(customerId).orElseThrow(
                () -> {
                    logger.error("Customer Not Found");
                    return new CustomerNotFoundException("Customer Not Found");
                }
        );
    }

    private void updateDobAndAge(Customer customer, LocalDate dob) {
        customer.setDob(dob);
        LocalDate currentDate = LocalDate.now();
        customer.setAge(Period.between(dob, currentDate).getYears());
    }
}