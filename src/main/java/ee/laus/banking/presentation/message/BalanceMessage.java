package ee.laus.banking.presentation.message;

import ee.laus.banking.domain.account.Balance;
import ee.laus.banking.domain.account.Currency;
import lombok.Data;

import java.io.Serializable;

@Data
public class BalanceMessage implements Serializable {
    private final Long id;
    private final double availableAmount;
    private final Currency currency;

    public static BalanceMessage of(Balance balance) {
        return new BalanceMessage(balance.getId(), balance.getAvailableAmount(), balance.getCurrency());
    }
}
