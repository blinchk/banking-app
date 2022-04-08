package ee.laus.banking.service;

import ee.laus.banking.dto.AccountCreateDto;
import ee.laus.banking.exception.InvalidCurrencyException;
import ee.laus.banking.exception.InvalidReferenceException;
import ee.laus.banking.exception.MissingDataException;
import ee.laus.banking.exception.account.AccountNotFoundException;
import ee.laus.banking.model.Account;
import ee.laus.banking.model.Balance;
import ee.laus.banking.model.Currency;
import ee.laus.banking.model.Customer;
import ee.laus.banking.repository.AccountRepository;
import ee.laus.banking.repository.CustomerRepository;
import ee.laus.banking.message.AccountQueue;
import ee.laus.banking.response.AccountResultResponse;
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
