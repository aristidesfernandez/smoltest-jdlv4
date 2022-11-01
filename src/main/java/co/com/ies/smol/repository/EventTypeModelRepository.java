package co.com.ies.smol.repository;

import co.com.ies.smol.domain.EventTypeModel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventTypeModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventTypeModelRepository extends JpaRepository<EventTypeModel, Long>, JpaSpecificationExecutor<EventTypeModel> {}
