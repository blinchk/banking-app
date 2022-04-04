package ee.laus.banking.infrastructure.account;

import ee.laus.banking.domain.account.Account;
import ee.laus.banking.infrastructure.BaseEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends BaseEntityRepository<Account> {
}
