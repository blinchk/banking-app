package ee.laus.banking.infrastructure.account;

import ee.laus.banking.exception.account.AccountNotFoundException;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
class BalanceServiceSaveTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BalanceService balanceService;

    private Balance balance;
    private Account account;

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
    void save_Test() {
        Currency expectedCurrency = balance.getCurrency();
        double expectedAvailableAmount = balance.getAvailableAmount();
        Balance actual = balanceService.save(balance);
        assertEquals(expectedCurrency, actual.getCurrency());
        assertEquals(expectedAvailableAmount, actual.getAvailableAmount());
    }

    @Test
    @Transactional
    void saveAllWithAccount_Success_Test() {
        Balance usdBalance = new Balance(1000, Currency.USD, account);
        List<Balance> balances = List.of(balance, usdBalance);
        Account expectedAccount = balance.getAccount();
        balances = balanceService.saveAllWithAccount(balances, account);
        balances.forEach(actual -> assertEquals(expectedAccount.getId(), actual.getAccount().getId()));
    }

    @Test
    void saveAllWithAccount_AccountNull_Test() {
        Balance usdBalance = new Balance(1000, Currency.USD);
        List<Balance> balances = List.of(balance, usdBalance);
        assertThrows(AccountNotFoundException.class, () -> balanceService.saveAllWithAccount(balances, null));
    }

    @Test
    void saveAllWithAccount_AccountNotFound_Test() {
        Account notExistingAccount = new Account();
        Balance usdBalance = new Balance(1000, Currency.USD);
        List<Balance> balances = List.of(balance, usdBalance);
        assertThrows(AccountNotFoundException.class,
                () -> balanceService.saveAllWithAccount(balances, notExistingAccount));
    }
}