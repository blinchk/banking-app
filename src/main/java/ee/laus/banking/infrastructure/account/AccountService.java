package ee.laus.banking.infrastructure.account;

import ee.laus.banking.app.account.dto.AccountCreateDto;
import ee.laus.banking.app.exception.InvalidCurrencyException;
import ee.laus.banking.app.exception.InvalidReferenceException;
import ee.laus.banking.app.exception.MissingDataException;
import ee.laus.banking.app.exception.account.AccountNotFoundException;
import ee.laus.banking.domain.account.Account;
import ee.laus.banking.domain.account.Balance;
import ee.laus.banking.domain.account.Currency;
import ee.laus.banking.domain.account.Customer;
import ee.laus.banking.infrastructure.account.balance.BalanceService;
import ee.laus.banking.infrastructure.account.customer.CustomerRepository;
import ee.laus.banking.infrastructure.account.queue.AccountQueue;
import ee.laus.banking.presentation.account.AccountResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final BalanceService balanceService;
    private final AccountQueue accountQueue;

    public AccountResultResponse create(AccountCreateDto dto) {
        if (dto.getCurrencies().isEmpty()) {
            throw new InvalidCurrencyException();
        }


        Customer customer;
        if (Objects.nonNull(dto.getCustomerId())) {
            customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(InvalidReferenceException::new);
        } else {
            throw new MissingDataException();
        }

        List<Balance> balances;
        try {
            balances = dto.getCurrencies().stream().map(currency -> new Balance(Currency.valueOf(currency))).toList();
        } catch (IllegalArgumentException e) {
            throw new InvalidCurrencyException();
        }

        Account account = new Account(customer, dto.getCountry(), balances, Collections.emptyList());
        account = accountRepository.save(account);
        AccountResultResponse response = AccountResultResponse.of(account);
        balances = balanceService.saveAllWithAccount(balances, account);
        account.setBalances(balances);
        accountQueue.publish(account);
        return response;
    }

    public AccountResultResponse get(Long id) {
        return AccountResultResponse.of(accountRepository.findById(id).orElseThrow(AccountNotFoundException::new));
    }
}
