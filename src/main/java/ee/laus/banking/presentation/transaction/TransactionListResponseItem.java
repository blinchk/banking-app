package ee.laus.banking.presentation.transaction;

import ee.laus.banking.domain.account.Currency;
import ee.laus.banking.domain.transaction.Transaction;
import ee.laus.banking.domain.transaction.TransactionDirection;
import lombok.Data;

import java.io.Serializable;

@Data
public class TransactionListResponseItem implements Serializable {
    private final Long accountId;
    private final Long transactionId;
    private final double amount;
    private final Currency currency;
    private final TransactionDirection direction;
    private final String description;

    public static TransactionListResponseItem of(Transaction transaction) {
        return new TransactionListResponseItem(transaction.getAccount().getId(),
                transaction.getId(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getDirection(),
                transaction.getDescription());
    }
}
