package ee.laus.banking.app.transaction.dto;

import ee.laus.banking.domain.account.Currency;
import ee.laus.banking.domain.transaction.TransactionDirection;
import lombok.Data;

import java.io.Serializable;

@Data
public class TransactionCreateDto implements Serializable {
    private final Long accountId;
    private final double amount;
    private final Currency currency;
    private final TransactionDirection direction;
    private final String description;
}
