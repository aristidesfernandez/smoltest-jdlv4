package co.com.ies.smol.service;

import co.com.ies.smol.domain.*; // for static metamodels
import co.com.ies.smol.domain.CurrencyType;
import co.com.ies.smol.repository.CurrencyTypeRepository;
import co.com.ies.smol.service.criteria.CurrencyTypeCriteria;
import co.com.ies.smol.service.dto.CurrencyTypeDTO;
import co.com.ies.smol.service.mapper.CurrencyTypeMapper;
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
 * Service for executing complex queries for {@link CurrencyType} entities in the database.
 * The main input is a {@link CurrencyTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CurrencyTypeDTO} or a {@link Page} of {@link CurrencyTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CurrencyTypeQueryService extends QueryService<CurrencyType> {

    private final Logger log = LoggerFactory.getLogger(CurrencyTypeQueryService.class);

    private final CurrencyTypeRepository currencyTypeRepository;

    private final CurrencyTypeMapper currencyTypeMapper;

    public CurrencyTypeQueryService(CurrencyTypeRepository currencyTypeRepository, CurrencyTypeMapper currencyTypeMapper) {
        this.currencyTypeRepository = currencyTypeRepository;
        this.currencyTypeMapper = currencyTypeMapper;
    }

    /**
     * Return a {@link List} of {@link CurrencyTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CurrencyTypeDTO> findByCriteria(CurrencyTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CurrencyType> specification = createSpecification(criteria);
        return currencyTypeMapper.toDto(currencyTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CurrencyTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CurrencyTypeDTO> findByCriteria(CurrencyTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CurrencyType> specification = createSpecification(criteria);
        return currencyTypeRepository.findAll(specification, page).map(currencyTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CurrencyTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CurrencyType> specification = createSpecification(criteria);
        return currencyTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link CurrencyTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CurrencyType> createSpecification(CurrencyTypeCriteria criteria) {
        Specification<CurrencyType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CurrencyType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CurrencyType_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CurrencyType_.code));
            }
            if (criteria.getSymbol() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSymbol(), CurrencyType_.symbol));
            }
            if (criteria.getIsPriority() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPriority(), CurrencyType_.isPriority));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), CurrencyType_.location));
            }
            if (criteria.getExchangeRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExchangeRate(), CurrencyType_.exchangeRate));
            }
            if (criteria.getDecimalPlaces() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDecimalPlaces(), CurrencyType_.decimalPlaces));
            }
            if (criteria.getDecimalSeparator() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDecimalSeparator(), CurrencyType_.decimalSeparator));
            }
            if (criteria.getThousandSeparator() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getThousandSeparator(), CurrencyType_.thousandSeparator));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CurrencyType_.description));
            }
            if (criteria.getEstablishmentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEstablishmentId(),
                            root -> root.join(CurrencyType_.establishment, JoinType.LEFT).get(Establishment_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
