package co.com.ies.smol.repository;

import co.com.ies.smol.domain.FormulaCounterType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FormulaCounterType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormulaCounterTypeRepository
    extends JpaRepository<FormulaCounterType, Long>, JpaSpecificationExecutor<FormulaCounterType> {}
