package co.com.ies.smol.service;

import co.com.ies.smol.domain.*; // for static metamodels
import co.com.ies.smol.domain.EventTypeModel;
import co.com.ies.smol.repository.EventTypeModelRepository;
import co.com.ies.smol.service.criteria.EventTypeModelCriteria;
import co.com.ies.smol.service.dto.EventTypeModelDTO;
import co.com.ies.smol.service.mapper.EventTypeModelMapper;
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
 * Service for executing complex queries for {@link EventTypeModel} entities in the database.
 * The main input is a {@link EventTypeModelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EventTypeModelDTO} or a {@link Page} of {@link EventTypeModelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EventTypeModelQueryService extends QueryService<EventTypeModel> {

    private final Logger log = LoggerFactory.getLogger(EventTypeModelQueryService.class);

    private final EventTypeModelRepository eventTypeModelRepository;

    private final EventTypeModelMapper eventTypeModelMapper;

    public EventTypeModelQueryService(EventTypeModelRepository eventTypeModelRepository, EventTypeModelMapper eventTypeModelMapper) {
        this.eventTypeModelRepository = eventTypeModelRepository;
        this.eventTypeModelMapper = eventTypeModelMapper;
    }

    /**
     * Return a {@link List} of {@link EventTypeModelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EventTypeModelDTO> findByCriteria(EventTypeModelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EventTypeModel> specification = createSpecification(criteria);
        return eventTypeModelMapper.toDto(eventTypeModelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EventTypeModelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EventTypeModelDTO> findByCriteria(EventTypeModelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EventTypeModel> specification = createSpecification(criteria);
        return eventTypeModelRepository.findAll(specification, page).map(eventTypeModelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EventTypeModelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EventTypeModel> specification = createSpecification(criteria);
        return eventTypeModelRepository.count(specification);
    }

    /**
     * Function to convert {@link EventTypeModelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EventTypeModel> createSpecification(EventTypeModelCriteria criteria) {
        Specification<EventTypeModel> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EventTypeModel_.id));
            }
            if (criteria.getModelId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModelId(), EventTypeModel_.modelId));
            }
            if (criteria.getEventTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEventTypeId(),
                            root -> root.join(EventTypeModel_.eventType, JoinType.LEFT).get(EventType_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
