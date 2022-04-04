package ee.laus.banking.infrastructure.account;

import ee.laus.banking.domain.account.Balance;
import ee.laus.banking.domain.account.Currency;
import ee.laus.banking.domain.transaction.TransactionDirection;
import ee.laus.banking.infrastructure.account.balance.BalanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
class BalanceServiceApplyTransactionTest {
    @Autowired
    private BalanceService balanceService;

    private Balance balance;

    @BeforeEach
    void setUp() {
        balance = new Balance();
        balance.setCurrency(Currency.USD);
        balance.setAvailableAmount(500);
    }

    @Test
    void applyTransaction_DirectionOut_Test() {
        final TransactionDirection direction = TransactionDirection.OUT;
        Balance actual = balanceService.applyTransaction(balance, 300, direction);
        Balance expected = new Balance();
        expected.setCurrency(Currency.USD);
        expected.setAvailableAmount(200);
        assertEquals(expected.getCurrency(), actual.getCurrency());
        assertEquals(expected.getAvailableAmount(), actual.getAvailableAmount());
    }

    @Test
    void applyTransaction_DirectionIn_Test() {
        final TransactionDirection direction = TransactionDirection.IN;
        Balance actual = balanceService.applyTransaction(balance, 300, direction);
        Balance expected = new Balance();
        expected.setCurrency(Currency.USD);
        expected.setAvailableAmount(800);
        assertEquals(expected.getCurrency(), actual.getCurrency());
        assertEquals(expected.getAvailableAmount(), actual.getAvailableAmount());
    }
}