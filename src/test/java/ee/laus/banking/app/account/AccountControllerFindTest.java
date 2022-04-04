package ee.laus.banking.app.account;

import ee.laus.banking.app.account.dto.AccountCreateDto;
import ee.laus.banking.app.exception.account.AccountNotFoundException;
import ee.laus.banking.domain.account.Customer;
import ee.laus.banking.infrastructure.account.AccountService;
import ee.laus.banking.infrastructure.account.customer.CustomerRepository;
import ee.laus.banking.presentation.account.AccountResultResponse;
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
class AccountControllerFindTest {
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
    @Transactional
    void findAccountById_Valid_Test() {
        Customer customer = customerRepository.save(new Customer());
        AccountCreateDto dto = new AccountCreateDto(customer.getId(), "Estonia", List.of("EUR", "USD"));
        AccountResultResponse expected = accountController.create(dto);
        AccountResultResponse actual = accountController.byId(expected.getAccountId());
        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    void findAccountById_NotFound_Test() {
        assertThrows(AccountNotFoundException.class, () -> accountController.byId(312L));
    }
}