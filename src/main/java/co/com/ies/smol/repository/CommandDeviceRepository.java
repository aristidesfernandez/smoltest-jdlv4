package co.com.ies.smol.repository;

import co.com.ies.smol.domain.CommandDevice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CommandDevice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandDeviceRepository extends JpaRepository<CommandDevice, Long>, JpaSpecificationExecutor<CommandDevice> {}
