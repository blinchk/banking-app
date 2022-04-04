package ee.laus.banking.domain.account;

import ee.laus.banking.domain.BaseEntity;
import ee.laus.banking.domain.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account extends BaseEntity {
    @ManyToOne
    private Customer customer;
    @NotBlank
    private String country;

    @OneToMany(mappedBy = "account")
    private List<Balance> balances;
    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;
}
