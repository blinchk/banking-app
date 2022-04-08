package ee.laus.banking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends BaseEntity {
    @ManyToOne
    private Account account;
    private double amount;
    private Currency currency;
    private TransactionDirection direction;
    private String description;
}
