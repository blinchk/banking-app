package ee.laus.banking.infrastructure.account.customer;

import ee.laus.banking.domain.account.Customer;
import ee.laus.banking.infrastructure.BaseEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends BaseEntityRepository<Customer> {
}
