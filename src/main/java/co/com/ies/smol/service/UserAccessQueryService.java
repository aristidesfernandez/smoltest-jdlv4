package co.com.ies.smol.service;

import co.com.ies.smol.domain.*; // for static metamodels
import co.com.ies.smol.domain.UserAccess;
import co.com.ies.smol.repository.UserAccessRepository;
import co.com.ies.smol.service.criteria.UserAccessCriteria;
import co.com.ies.smol.service.dto.UserAccessDTO;
import co.com.ies.smol.service.mapper.UserAccessMapper;
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
 * Service for executing complex queries for {@link UserAccess} entities in the database.
 * The main input is a {@link UserAccessCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserAccessDTO} or a {@link Page} of {@link UserAccessDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserAccessQueryService extends QueryService<UserAccess> {

    private final Logger log = LoggerFactory.getLogger(UserAccessQueryService.class);

    private final UserAccessRepository userAccessRepository;

    private final UserAccessMapper userAccessMapper;

    public UserAccessQueryService(UserAccessRepository userAccessRepository, UserAccessMapper userAccessMapper) {
        this.userAccessRepository = userAccessRepository;
        this.userAccessMapper = userAccessMapper;
    }

    /**
     * Return a {@link List} of {@link UserAccessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserAccessDTO> findByCriteria(UserAccessCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserAccess> specification = createSpecification(criteria);
        return userAccessMapper.toDto(userAccessRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserAccessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserAccessDTO> findByCriteria(UserAccessCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserAccess> specification = createSpecification(criteria);
        return userAccessRepository.findAll(specification, page).map(userAccessMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserAccessCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserAccess> specification = createSpecification(criteria);
        return userAccessRepository.count(specification);
    }

    /**
     * Function to convert {@link UserAccessCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserAccess> createSpecification(UserAccessCriteria criteria) {
        Specification<UserAccess> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserAccess_.id));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), UserAccess_.username));
            }
            if (criteria.getIpAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIpAddress(), UserAccess_.ipAddress));
            }
            if (criteria.getRegistrationAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegistrationAt(), UserAccess_.registrationAt));
            }
        }
        return specification;
    }
}
