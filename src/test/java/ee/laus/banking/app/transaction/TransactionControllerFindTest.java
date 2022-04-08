package ee.laus.banking.app.transaction;

import ee.laus.banking.controller.TransactionController;
import ee.laus.banking.exception.account.AccountNotFoundException;
import ee.laus.banking.model.Account;
import ee.laus.banking.model.Currency;
import ee.laus.banking.model.Transaction;
import ee.laus.banking.repository.AccountRepository;
import ee.laus.banking.repository.TransactionRepository;
import ee.laus.banking.response.TransactionListResponse;
import ee.laus.banking.response.TransactionListResponseItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class TransactionControllerFindTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionController transactionController;
    @Autowired
    private TransactionRepository transactionRepository;

    private Account account;

    @BeforeEach
    void setUp() {
        account = accountRepository.save(new Account());
        Transaction eurTransaction = new Transaction();
        eurTransaction.setAmount(500);
        eurTransaction.setCurrency(Currency.EUR);
        eurTransaction.setAccount(account);
        Transaction usdTransaction = new Transaction();
        usdTransaction.setAmount(100);
        usdTransaction.setCurrency(Currency.USD);
        usdTransaction.setAccount(account);
        transactionRepository.saveAll(List.of(usdTransaction, eurTransaction));
    }

    @Test
    void allByAccountId_Valid_Test() {
        TransactionListResponse transactionsResponse = transactionController.allByAccountId(account.getId());
        int expectedCount = 2;
        List<TransactionListResponseItem> actual = transactionsResponse.getTransactions();
        int actualCount = actual.size();
        assertEquals(expectedCount, actualCount);
        assertTrue(actual.stream().anyMatch(transaction -> transaction.getCurrency() == Currency.USD));
        assertTrue(actual.stream().anyMatch(transaction -> transaction.getCurrency() == Currency.EUR));
        assertTrue(actual.stream().anyMatch(transaction -> transaction.getAmount() == 100));
        assertTrue(actual.stream().anyMatch(transaction -> transaction.getAmount() == 500));
    }

    @Test
    void allByAccountId_AccountNotFound_Test() {
        Account invalidAccount = new Account();
        invalidAccount.setId(23L);
        assertThrows(AccountNotFoundException.class,
                () -> transactionController.allByAccountId(invalidAccount.getId()));
    }
}