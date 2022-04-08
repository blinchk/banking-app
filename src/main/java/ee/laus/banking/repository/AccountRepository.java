package ee.laus.banking.repository;

import ee.laus.banking.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends BaseEntityRepository<Account> {
}
