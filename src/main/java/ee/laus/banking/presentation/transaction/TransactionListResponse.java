package ee.laus.banking.presentation.transaction;

import ee.laus.banking.domain.transaction.Transaction;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TransactionListResponse implements Serializable {
    private final List<TransactionListResponseItem> transactions;

    public static TransactionListResponse of(List<Transaction> transactions) {
        return new TransactionListResponse(transactions.stream().map(TransactionListResponseItem::of).toList());
    }
}
