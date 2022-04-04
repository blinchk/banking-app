package ee.laus.banking.infrastructure.account.balance;

import ee.laus.banking.domain.account.Account;
import ee.laus.banking.domain.account.Balance;
import ee.laus.banking.domain.account.Currency;
import ee.laus.banking.infrastructure.BaseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceRepository extends BaseEntityRepository<Balance> {
    Optional<Balance> findFirstByAccountAndCurrency(Account account, Currency currency);
}
