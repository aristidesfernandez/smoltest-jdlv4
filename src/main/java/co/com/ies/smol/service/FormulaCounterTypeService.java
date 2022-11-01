package co.com.ies.smol.service;

import co.com.ies.smol.service.dto.FormulaCounterTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.com.ies.smol.domain.FormulaCounterType}.
 */
public interface FormulaCounterTypeService {
    /**
     * Save a formulaCounterType.
     *
     * @param formulaCounterTypeDTO the entity to save.
     * @return the persisted entity.
     */
    FormulaCounterTypeDTO save(FormulaCounterTypeDTO formulaCounterTypeDTO);

    /**
     * Updates a formulaCounterType.
     *
     * @param formulaCounterTypeDTO the entity to update.
     * @return the persisted entity.
     */
    FormulaCounterTypeDTO update(FormulaCounterTypeDTO formulaCounterTypeDTO);

    /**
     * Partially updates a formulaCounterType.
     *
     * @param formulaCounterTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FormulaCounterTypeDTO> partialUpdate(FormulaCounterTypeDTO formulaCounterTypeDTO);

    /**
     * Get all the formulaCounterTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormulaCounterTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" formulaCounterType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormulaCounterTypeDTO> findOne(Long id);

    /**
     * Delete the "id" formulaCounterType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
