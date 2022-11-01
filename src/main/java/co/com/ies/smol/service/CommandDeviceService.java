package co.com.ies.smol.service;

import co.com.ies.smol.service.dto.CommandDeviceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.com.ies.smol.domain.CommandDevice}.
 */
public interface CommandDeviceService {
    /**
     * Save a commandDevice.
     *
     * @param commandDeviceDTO the entity to save.
     * @return the persisted entity.
     */
    CommandDeviceDTO save(CommandDeviceDTO commandDeviceDTO);

    /**
     * Updates a commandDevice.
     *
     * @param commandDeviceDTO the entity to update.
     * @return the persisted entity.
     */
    CommandDeviceDTO update(CommandDeviceDTO commandDeviceDTO);

    /**
     * Partially updates a commandDevice.
     *
     * @param commandDeviceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CommandDeviceDTO> partialUpdate(CommandDeviceDTO commandDeviceDTO);

    /**
     * Get all the commandDevices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CommandDeviceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" commandDevice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommandDeviceDTO> findOne(Long id);

    /**
     * Delete the "id" commandDevice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
