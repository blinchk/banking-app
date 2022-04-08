package ee.laus.banking.response;

import ee.laus.banking.model.Balance;
import ee.laus.banking.model.Currency;
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
