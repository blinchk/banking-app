package ee.laus.banking.service;

import ee.laus.banking.dto.TransactionCreateDto;
import ee.laus.banking.exception.MissingDataException;
import ee.laus.banking.exception.account.AccountNotFoundException;
import ee.laus.banking.exception.transaction.InvalidAmountException;
import ee.laus.banking.message.TransactionQueue;
import ee.laus.banking.model.*;
import ee.laus.banking.repository.AccountRepository;
import ee.laus.banking.repository.TransactionRepository;
import ee.laus.banking.response.BalanceResponse;
import ee.laus.banking.response.TransactionListResponse;
import ee.laus.banking.response.TransactionResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final BalanceService balanceService;
    private final TransactionQueue transactionQueue;

    public TransactionListResponse allByAccountId(Long accountId) {
        Account account = accountRepository.findById(accountId)
                                           .orElseThrow(AccountNotFoundException::new);
        return TransactionListResponse.of(transactionRepository.findAllByAccount(account));
    }

    public Transaction save(Transaction transaction) {
        transaction = transactionRepository.save(transaction);
        transactionQueue.publish(transaction);
        return transaction;
    }

    public TransactionResultResponse create(TransactionCreateDto dto) {
        double amount = dto.getAmount();
        if (amount < 0) {
            throw new InvalidAmountException();
        }
        final String description = dto.getDescription();
        if (Objects.isNull(description) || description.isBlank() || description.isEmpty()) {
            throw new MissingDataException();
        }
        final TransactionDirection direction = dto.getDirection();
        final Currency currency = dto.getCurrency();
        final Account account = accountRepository.findById(dto.getAccountId())
                                                 .orElseThrow(AccountNotFoundException::new);
        final Balance balance = direction == TransactionDirection.OUT ? balanceService.validateSufficientFunds(account,
                amount,
                currency) : balanceService.findByAccountAndCurrency(account, currency);
        final Balance balanceAfterTransaction = balanceService.applyTransaction(balance, amount, direction);

        Transaction transaction = new Transaction(account, amount, currency, direction, description);
        transaction = save(transaction);

        return new TransactionResultResponse(transaction.getAccount()
                                                        .getId(),
                transaction.getId(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getDirection(),
                transaction.getDescription(),
                BalanceResponse.of(balanceAfterTransaction));
    }
}
