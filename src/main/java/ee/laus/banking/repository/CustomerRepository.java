package ee.laus.banking.repository;

import ee.laus.banking.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends BaseEntityRepository<Customer> {
}
