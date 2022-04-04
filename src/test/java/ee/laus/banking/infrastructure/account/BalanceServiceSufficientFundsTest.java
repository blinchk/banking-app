package ee.laus.banking.infrastructure.account;

import ee.laus.banking.app.exception.InvalidCurrencyException;
import ee.laus.banking.app.exception.transaction.InsufficientFundsException;
import ee.laus.banking.domain.account.Account;
import ee.laus.banking.domain.account.Balance;
import ee.laus.banking.domain.account.Currency;
import ee.laus.banking.domain.account.Customer;
import ee.laus.banking.infrastructure.account.balance.BalanceService;
import ee.laus.banking.infrastructure.account.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
class BalanceServiceSufficientFundsTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BalanceService balanceService;

    private Account account;
    private Balance balance;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customer = customerRepository.save(customer);
        account = new Account();
        account.setCountry("Estonia");
        account.setCustomer(customer);
        account = accountRepository.save(account);
        balance = new Balance(2000, Currency.EUR, account);
        balanceService.save(balance);
    }

    @Test
    @Transactional
    void validateSufficientFund_Success_Test() {
        Balance actual = balanceService.validateSufficientFunds(account, 1000, Currency.EUR);
        Account expectedAccount = balance.getAccount();
        Account actualAccount = actual.getAccount();
        double expectedAvailableAmount = balance.getAvailableAmount();
        double actualAvailableAmount = actual.getAvailableAmount();
        Currency expectedCurrency = balance.getCurrency();
        Currency actualCurrency = actual.getCurrency();
        assertEquals(expectedAccount, actualAccount);
        assertEquals(expectedAvailableAmount, actualAvailableAmount);
        assertEquals(expectedCurrency, actualCurrency);
    }

    @Test
    void validateSufficientFund_InvalidCurrency_Test() {
        assertThrows(InvalidCurrencyException.class,
                () -> balanceService.validateSufficientFunds(account, 1000, Currency.USD));
    }

    @Test
    void validateSufficientFund_Failure_Test() {
        assertThrows(InsufficientFundsException.class,
                () -> balanceService.validateSufficientFunds(account, 3000, Currency.EUR));
    }
}