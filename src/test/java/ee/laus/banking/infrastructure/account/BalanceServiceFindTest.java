package ee.laus.banking.infrastructure.account;

import ee.laus.banking.app.exception.account.AccountNotFoundException;
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
class BalanceServiceFindTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BalanceService balanceService;

    private Account account;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customer = customerRepository.save(customer);
        account = new Account();
        account.setCountry("Estonia");
        account.setCustomer(customer);
        account = accountRepository.save(account);
        Balance balance = new Balance(2000, Currency.EUR, account);
        balanceService.save(balance);
    }

    @Transactional
    @Test
    void findByAccountAndCurrency_Success_Test() {
        final Currency currency = Currency.EUR;
        final double expectedAvailableAmount = 2000;
        final Long expectedAccountId = account.getId();
        Balance actual = balanceService.findByAccountAndCurrency(account, currency);
        assertEquals(expectedAvailableAmount, actual.getAvailableAmount());
        assertEquals(expectedAccountId, actual.getAccount().getId());
    }

    @Test
    void findByAccountAndCurrency_Exception_Test() {
        Account accountNotExists = new Account();
        Currency currency = Currency.GBP;
        assertThrows(AccountNotFoundException.class,
                () -> balanceService.findByAccountAndCurrency(accountNotExists, currency));
    }
}