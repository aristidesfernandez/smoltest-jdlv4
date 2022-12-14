package co.com.ies.smol.repository;

import co.com.ies.smol.domain.DeviceCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DeviceCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceCategoryRepository extends JpaRepository<DeviceCategory, Long>, JpaSpecificationExecutor<DeviceCategory> {}
