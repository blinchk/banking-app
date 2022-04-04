package ee.laus.banking.infrastructure.transaction;

import ee.laus.banking.domain.account.Account;
import ee.laus.banking.domain.transaction.Transaction;
import ee.laus.banking.infrastructure.BaseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends BaseEntityRepository<Transaction> {
    List<Transaction> findAllByAccount(Account account);
}
