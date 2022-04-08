package ee.laus.banking.repository;

import ee.laus.banking.model.Account;
import ee.laus.banking.model.Balance;
import ee.laus.banking.model.Currency;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceRepository extends BaseEntityRepository<Balance> {
    Optional<Balance> findFirstByAccountAndCurrency(Account account, Currency currency);
}
