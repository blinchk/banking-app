package ee.laus.banking.presentation.message;

import ee.laus.banking.domain.account.Currency;
import ee.laus.banking.domain.transaction.Transaction;
import ee.laus.banking.domain.transaction.TransactionDirection;
import lombok.Data;

import java.io.Serializable;

@Data
public class TransactionMessage implements Serializable {
    private final Long accountId;
    private final double amount;
    private final Currency currency;
    private final TransactionDirection direction;
    private final String description;

    public static TransactionMessage of(Transaction transaction) {
        return new TransactionMessage(transaction.getAccount().getId(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getDirection(),
                transaction.getDescription());
    }
}
