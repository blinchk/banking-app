package ee.laus.banking.infrastructure.account;

import ee.laus.banking.exception.InvalidCurrencyException;
import ee.laus.banking.exception.transaction.InsufficientFundsException;
import ee.laus.banking.model.Account;
import ee.laus.banking.model.Balance;
import ee.laus.banking.model.Currency;
import ee.laus.banking.model.Customer;
import ee.laus.banking.service.BalanceService;
import ee.laus.banking.repository.AccountRepository;
import ee.laus.banking.repository.CustomerRepository;
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