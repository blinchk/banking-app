package ee.laus.banking.domain.account;

import ee.laus.banking.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Balance extends BaseEntity {
    private double availableAmount;
    @Enumerated(value = EnumType.STRING)
    private Currency currency;
    @ManyToOne
    private Account account;

    public Balance(double availableAmount, Currency currency) {
        this.availableAmount = availableAmount;
        this.currency = currency;
    }

    public Balance(Currency currency) {
        this.availableAmount = DEFAULT_AMOUNT;
        this.currency = currency;
    }

    private static final double DEFAULT_AMOUNT = 0;
}
