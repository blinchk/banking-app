package ee.laus.banking.presentation.transaction;

import ee.laus.banking.app.exception.InvalidCurrencyException;
import ee.laus.banking.domain.account.Balance;
import ee.laus.banking.domain.account.Currency;
import ee.laus.banking.domain.transaction.Transaction;
import ee.laus.banking.domain.transaction.TransactionDirection;
import ee.laus.banking.presentation.balance.BalanceResponse;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;

@Data
public class TransactionResultResponse implements Serializable {
    private final Long accountId;
    private final Long transactionId;
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private final double amount;
    private final Currency currency;
    private final TransactionDirection direction;
    private final String description;
    private final BalanceResponse balanceAfterTransaction;

    public static TransactionResultResponse of(Transaction transaction) {
        final Balance balance = transaction.getAccount()
                .getBalances()
                .stream()
                .filter(b -> b.getCurrency() == transaction.getCurrency())
                .findFirst()
                .orElseThrow(InvalidCurrencyException::new);
        balance.setAvailableAmount(balance.getAvailableAmount() - transaction.getAmount());

        return new TransactionResultResponse(transaction.getAccount().getId(),
                transaction.getId(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getDirection(),
                transaction.getDescription(),
                BalanceResponse.of(balance));
    }
}
