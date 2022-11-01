package co.com.ies.smol.service;

import co.com.ies.smol.domain.*; // for static metamodels
import co.com.ies.smol.domain.CommandDevice;
import co.com.ies.smol.repository.CommandDeviceRepository;
import co.com.ies.smol.service.criteria.CommandDeviceCriteria;
import co.com.ies.smol.service.dto.CommandDeviceDTO;
import co.com.ies.smol.service.mapper.CommandDeviceMapper;
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
 * Service for executing complex queries for {@link CommandDevice} entities in the database.
 * The main input is a {@link CommandDeviceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommandDeviceDTO} or a {@link Page} of {@link CommandDeviceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommandDeviceQueryService extends QueryService<CommandDevice> {

    private final Logger log = LoggerFactory.getLogger(CommandDeviceQueryService.class);

    private final CommandDeviceRepository commandDeviceRepository;

    private final CommandDeviceMapper commandDeviceMapper;

    public CommandDeviceQueryService(CommandDeviceRepository commandDeviceRepository, CommandDeviceMapper commandDeviceMapper) {
        this.commandDeviceRepository = commandDeviceRepository;
        this.commandDeviceMapper = commandDeviceMapper;
    }

    /**
     * Return a {@link List} of {@link CommandDeviceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommandDeviceDTO> findByCriteria(CommandDeviceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommandDevice> specification = createSpecification(criteria);
        return commandDeviceMapper.toDto(commandDeviceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommandDeviceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommandDeviceDTO> findByCriteria(CommandDeviceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommandDevice> specification = createSpecification(criteria);
        return commandDeviceRepository.findAll(specification, page).map(commandDeviceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommandDeviceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommandDevice> specification = createSpecification(criteria);
        return commandDeviceRepository.count(specification);
    }

    /**
     * Function to convert {@link CommandDeviceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CommandDevice> createSpecification(CommandDeviceCriteria criteria) {
        Specification<CommandDevice> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CommandDevice_.id));
            }
            if (criteria.getCommandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCommandId(),
                            root -> root.join(CommandDevice_.command, JoinType.LEFT).get(Command_.id)
                        )
                    );
            }
            if (criteria.getDeviceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDeviceId(), root -> root.join(CommandDevice_.device, JoinType.LEFT).get(Device_.id))
                    );
            }
        }
        return specification;
    }
}
