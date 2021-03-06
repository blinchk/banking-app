package ee.laus.banking.app.transaction;

import ee.laus.banking.controller.TransactionController;
import ee.laus.banking.dto.TransactionCreateDto;
import ee.laus.banking.model.Account;
import ee.laus.banking.model.Balance;
import ee.laus.banking.model.Currency;
import ee.laus.banking.model.TransactionDirection;
import ee.laus.banking.repository.AccountRepository;
import ee.laus.banking.repository.BalanceRepository;
import ee.laus.banking.response.TransactionResultResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
class TransactionControllerCreateTest {
    @Autowired
    private TransactionController transactionController;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account = accountRepository.save(account);
        Balance balance = new Balance(300, Currency.EUR, account);
        balanceRepository.save(balance);
    }

    @Test
    void create_Success_DirectionIn_Test() {
        TransactionCreateDto dto = new TransactionCreateDto(account.getId(),
                200,
                Currency.EUR,
                TransactionDirection.IN,
                "Lunch");

        TransactionResultResponse response = transactionController.create(dto);

        double expectedAvailableAmount = 500;
        Currency expectedCurrency = Currency.EUR;

        assertEquals(account.getId(), response.getAccountId());
        assertEquals(dto.getAmount(), response.getAmount());
        assertEquals(dto.getCurrency(), response.getCurrency());
        assertEquals(dto.getDirection(), response.getDirection());
        assertEquals(dto.getDescription(), response.getDescription());
        assertEquals(expectedAvailableAmount, response.getBalanceAfterTransaction().getAvailableAmount());
        assertEquals(expectedCurrency, response.getBalanceAfterTransaction().getCurrency());
    }

    @Test
    void create_Success_DirectionOut_Test() {
        TransactionCreateDto dto = new TransactionCreateDto(account.getId(),
                200,
                Currency.EUR,
                TransactionDirection.OUT,
                "Lunch");

        TransactionResultResponse response = transactionController.create(dto);

        double expectedAvailableAmount = 100;
        Currency expectedCurrency = Currency.EUR;

        assertEquals(account.getId(), response.getAccountId());
        assertEquals(dto.getAmount(), response.getAmount());
        assertEquals(expectedCurrency, response.getCurrency());
        assertEquals(dto.getDirection(), response.getDirection());
        assertEquals(dto.getDescription(), response.getDescription());
        assertEquals(expectedAvailableAmount, response.getBalanceAfterTransaction().getAvailableAmount());
        assertEquals(expectedCurrency, response.getBalanceAfterTransaction().getCurrency());
    }
}