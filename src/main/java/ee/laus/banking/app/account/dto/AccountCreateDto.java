package ee.laus.banking.app.account.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AccountCreateDto implements Serializable {
    private final Long customerId;
    private final String country;
    private final List<String> currencies;
}
