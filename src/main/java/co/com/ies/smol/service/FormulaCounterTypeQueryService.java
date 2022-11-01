package co.com.ies.smol.service;

import co.com.ies.smol.domain.*; // for static metamodels
import co.com.ies.smol.domain.FormulaCounterType;
import co.com.ies.smol.repository.FormulaCounterTypeRepository;
import co.com.ies.smol.service.criteria.FormulaCounterTypeCriteria;
import co.com.ies.smol.service.dto.FormulaCounterTypeDTO;
import co.com.ies.smol.service.mapper.FormulaCounterTypeMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link FormulaCounterType} entities in the database.
 * The main input is a {@link FormulaCounterTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FormulaCounterTypeDTO} or a {@link Page} of {@link FormulaCounterTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FormulaCounterTypeQueryService extends QueryService<FormulaCounterType> {

    private final Logger log = LoggerFactory.getLogger(FormulaCounterTypeQueryService.class);

    private final FormulaCounterTypeRepository formulaCounterTypeRepository;

    private final FormulaCounterTypeMapper formulaCounterTypeMapper;

    public FormulaCounterTypeQueryService(
        FormulaCounterTypeRepository formulaCounterTypeRepository,
        FormulaCounterTypeMapper formulaCounterTypeMapper
    ) {
        this.formulaCounterTypeRepository = formulaCounterTypeRepository;
        this.formulaCounterTypeMapper = formulaCounterTypeMapper;
    }

    /**
     * Return a {@link List} of {@link FormulaCounterTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FormulaCounterTypeDTO> findByCriteria(FormulaCounterTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FormulaCounterType> specification = createSpecification(criteria);
        return formulaCounterTypeMapper.toDto(formulaCounterTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FormulaCounterTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FormulaCounterTypeDTO> findByCriteria(FormulaCounterTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FormulaCounterType> specification = createSpecification(criteria);
        return formulaCounterTypeRepository.findAll(specification, page).map(formulaCounterTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FormulaCounterTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FormulaCounterType> specification = createSpecification(criteria);
        return formulaCounterTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link FormulaCounterTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FormulaCounterType> createSpecification(FormulaCounterTypeCriteria criteria) {
        Specification<FormulaCounterType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FormulaCounterType_.id));
            }
            if (criteria.getFormulaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFormulaId(),
                            root -> root.join(FormulaCounterType_.formula, JoinType.LEFT).get(Formula_.id)
                        )
                    );
            }
            if (criteria.getCounterTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCounterTypeId(),
                            root -> root.join(FormulaCounterType_.counterType, JoinType.LEFT).get(CounterType_.counterCode)
                        )
                    );
            }
        }
        return specification;
    }
}
