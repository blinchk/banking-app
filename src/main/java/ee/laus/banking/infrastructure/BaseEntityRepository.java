package ee.laus.banking.infrastructure;

import ee.laus.banking.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseEntityRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
}
