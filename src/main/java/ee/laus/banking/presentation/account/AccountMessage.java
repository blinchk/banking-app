package ee.laus.banking.presentation.account;

import ee.laus.banking.domain.account.Account;
import lombok.Data;

import java.io.Serializable;

@Data
public class AccountMessage implements Serializable {
    private final Long id;
    private final Long customerId;
    private final String country;

    public static AccountMessage of(Account account) {
        return new AccountMessage(account.getId(), account.getCustomer().getId(), account.getCountry());
    }
}
