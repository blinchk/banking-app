package ee.laus.banking.service;

import ee.laus.banking.message.CustomerQueue;
import ee.laus.banking.model.Customer;
import ee.laus.banking.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerQueue customerQueue;

    public Customer save(Customer customer) {
        customer = customerRepository.save(customer);
        customerQueue.publish(customer);
        return customer;
    }
}
