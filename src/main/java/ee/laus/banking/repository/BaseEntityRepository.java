package ee.laus.banking.repository;

import ee.laus.banking.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseEntityRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
}
