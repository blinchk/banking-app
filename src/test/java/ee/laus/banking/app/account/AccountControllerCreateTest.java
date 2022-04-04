package ee.laus.banking.app.account;

import ee.laus.banking.app.account.dto.AccountCreateDto;
import ee.laus.banking.app.exception.InvalidCurrencyException;
import ee.laus.banking.app.exception.InvalidReferenceException;
import ee.laus.banking.app.exception.MissingDataException;
import ee.laus.banking.domain.account.Currency;
import ee.laus.banking.domain.account.Customer;
import ee.laus.banking.infrastructure.account.AccountService;
import ee.laus.banking.infrastructure.account.customer.CustomerRepository;
import ee.laus.banking.presentation.account.AccountResultResponse;
import ee.laus.banking.presentation.balance.BalanceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class AccountControllerCreateTest {
    @Autowired
    private AccountController accountController;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        this.accountController = new AccountController(accountService);
    }

    @Test
    void createAccount_Valid_Test() {
        Customer customer = customerRepository.save(new Customer());
        AccountCreateDto dto = new AccountCreateDto(customer.getId(), "Estonia", List.of("EUR", "USD"));
        AccountResultResponse actual = accountController.create(dto);
        AccountResultResponse expected = new AccountResultResponse(1L,
                customer.getId(),
                List.of(new BalanceResponse(0, Currency.EUR), new BalanceResponse(0, Currency.USD)));
        assertArrayEquals(expected.getBalances().toArray(new BalanceResponse[0]),
                actual.getBalances().toArray(new BalanceResponse[0]));
        assertEquals(expected.getCustomerId(), actual.getCustomerId());
    }

    @Test
    void createAccount_WithoutCurrencies_Test() {
        Customer customer = customerRepository.save(new Customer());
        AccountCreateDto dto = new AccountCreateDto(customer.getId(), "Estonia", emptyList());
        assertThrows(InvalidCurrencyException.class, () -> accountController.create(dto));
    }

    @Test
    void createAccount_WithCustomerNotExists_Test() {
        AccountCreateDto dto = new AccountCreateDto(1223L, "Estonia", List.of("EUR", "USD"));
        assertThrows(InvalidReferenceException.class, () -> accountController.create(dto));
    }

    @Test
    void createAccount_WithNullCustomer_Test() {
        AccountCreateDto dto = new AccountCreateDto(null, "Estonia", List.of("EUR", "USD"));
        assertThrows(MissingDataException.class, () -> accountController.create(dto));
    }
}