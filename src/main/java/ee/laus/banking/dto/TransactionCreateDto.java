package ee.laus.banking.dto;

import ee.laus.banking.model.Currency;
import ee.laus.banking.model.TransactionDirection;
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
