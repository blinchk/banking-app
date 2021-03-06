package ee.laus.banking.response;

import ee.laus.banking.model.Currency;
import ee.laus.banking.model.Transaction;
import ee.laus.banking.model.TransactionDirection;
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
        return new TransactionListResponseItem(transaction.getAccount()
                                                          .getId(),
                transaction.getId(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getDirection(),
                transaction.getDescription());
    }
}
