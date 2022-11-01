package co.com.ies.smol.service;

import co.com.ies.smol.service.dto.EventTypeModelDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.com.ies.smol.domain.EventTypeModel}.
 */
public interface EventTypeModelService {
    /**
     * Save a eventTypeModel.
     *
     * @param eventTypeModelDTO the entity to save.
     * @return the persisted entity.
     */
    EventTypeModelDTO save(EventTypeModelDTO eventTypeModelDTO);

    /**
     * Updates a eventTypeModel.
     *
     * @param eventTypeModelDTO the entity to update.
     * @return the persisted entity.
     */
    EventTypeModelDTO update(EventTypeModelDTO eventTypeModelDTO);

    /**
     * Partially updates a eventTypeModel.
     *
     * @param eventTypeModelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EventTypeModelDTO> partialUpdate(EventTypeModelDTO eventTypeModelDTO);

    /**
     * Get all the eventTypeModels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EventTypeModelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" eventTypeModel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventTypeModelDTO> findOne(Long id);

    /**
     * Delete the "id" eventTypeModel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
