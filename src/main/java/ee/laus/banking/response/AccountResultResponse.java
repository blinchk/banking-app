package ee.laus.banking.response;

import ee.laus.banking.model.Account;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AccountResultResponse implements Serializable {
    private final Long accountId;
    private final Long customerId;
    private final List<BalanceResponse> balances;

    public static AccountResultResponse of(Account account) {
        return new AccountResultResponse(account.getId(),
                account.getCustomer()
                       .getId(),
                account.getBalances()
                       .stream()
                       .map(BalanceResponse::of)
                       .collect(Collectors.toList()));
    }
}
