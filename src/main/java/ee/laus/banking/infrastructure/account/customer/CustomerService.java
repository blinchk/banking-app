package ee.laus.banking.infrastructure.account.customer;

import ee.laus.banking.domain.account.Customer;
import ee.laus.banking.infrastructure.account.customer.queue.CustomerQueue;
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
