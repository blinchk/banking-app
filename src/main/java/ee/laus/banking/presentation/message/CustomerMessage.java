package ee.laus.banking.presentation.message;

import ee.laus.banking.domain.account.Customer;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerMessage implements Serializable {
    private final Long id;

    public static CustomerMessage of(Customer customer) {
        return new CustomerMessage(customer.getId());
    }
}
