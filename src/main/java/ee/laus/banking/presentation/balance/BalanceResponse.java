package ee.laus.banking.presentation.balance;

import ee.laus.banking.domain.account.Balance;
import ee.laus.banking.domain.account.Currency;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;

@Data
public class BalanceResponse implements Serializable {
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private final double availableAmount;
    private final Currency currency;

    public static BalanceResponse of(Balance balance) {
        return new BalanceResponse(balance.getAvailableAmount(), balance.getCurrency());
    }
}
