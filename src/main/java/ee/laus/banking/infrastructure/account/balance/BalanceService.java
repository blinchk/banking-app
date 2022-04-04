package ee.laus.banking.infrastructure.account.balance;

import ee.laus.banking.app.exception.InvalidCurrencyException;
import ee.laus.banking.app.exception.account.AccountNotFoundException;
import ee.laus.banking.app.exception.transaction.InsufficientFundsException;
import ee.laus.banking.domain.account.Account;
import ee.laus.banking.domain.account.Balance;
import ee.laus.banking.domain.account.Currency;
import ee.laus.banking.domain.transaction.TransactionDirection;
import ee.laus.banking.infrastructure.account.balance.queue.BalanceQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;
    private final BalanceQueue balanceSaveQueue;

    public Balance validateSufficientFunds(Account account, double amount, Currency currency) {
        Balance balance = findByAccountAndCurrency(account, currency);
        if (amount <= balance.getAvailableAmount()) {
            return balance;
        } else {
            throw new InsufficientFundsException();
        }
    }

    public Balance save(Balance balance) {
        balance = balanceRepository.save(balance);
        balanceSaveQueue.publish(balance);
        return balance;
    }

    public Balance applyTransaction(Balance balance, double amount, TransactionDirection direction) {
        final double availableAmount = balance.getAvailableAmount();
        final double availableAmountAfterTransaction;
        if (direction == TransactionDirection.IN) {
            availableAmountAfterTransaction = availableAmount + amount;
        } else {
            availableAmountAfterTransaction = availableAmount - amount;
        }
        balance.setAvailableAmount(availableAmountAfterTransaction);

        return save(balance);
    }

    public Balance findByAccountAndCurrency(Account account, Currency currency) {
        try {
            return balanceRepository.findFirstByAccountAndCurrency(account, currency)
                    .orElseThrow(InvalidCurrencyException::new);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new AccountNotFoundException();
        }
    }

    public List<Balance> saveAllWithAccount(List<Balance> balances, Account account) {
        if (Objects.isNull(account)) {
            throw new AccountNotFoundException();
        }
        balances = balances.stream().peek(balance -> balance.setAccount(account)).toList();
        try {
            balances = balanceRepository.saveAll(balances);
            balances.forEach(balanceSaveQueue::publish);
            return balances;
        } catch (InvalidDataAccessApiUsageException e) {
            throw new AccountNotFoundException();
        }
    }
}
