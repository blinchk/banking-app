package ee.laus.banking.repository;

import ee.laus.banking.model.Account;
import ee.laus.banking.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends BaseEntityRepository<Transaction> {
    List<Transaction> findAllByAccount(Account account);
}
