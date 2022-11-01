package co.com.ies.smol.web.rest;

import co.com.ies.smol.repository.EventTypeModelRepository;
import co.com.ies.smol.service.EventTypeModelQueryService;
import co.com.ies.smol.service.EventTypeModelService;
import co.com.ies.smol.service.criteria.EventTypeModelCriteria;
import co.com.ies.smol.service.dto.EventTypeModelDTO;
import co.com.ies.smol.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.com.ies.smol.domain.EventTypeModel}.
 */
@RestController
@RequestMapping("/api")
public class EventTypeModelResource {

    private final Logger log = LoggerFactory.getLogger(EventTypeModelResource.class);

    private static final String ENTITY_NAME = "eventTypeModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventTypeModelService eventTypeModelService;

    private final EventTypeModelRepository eventTypeModelRepository;

    private final EventTypeModelQueryService eventTypeModelQueryService;

    public EventTypeModelResource(
        EventTypeModelService eventTypeModelService,
        EventTypeModelRepository eventTypeModelRepository,
        EventTypeModelQueryService eventTypeModelQueryService
    ) {
        this.eventTypeModelService = eventTypeModelService;
        this.eventTypeModelRepository = eventTypeModelRepository;
        this.eventTypeModelQueryService = eventTypeModelQueryService;
    }

    /**
     * {@code POST  /event-type-models} : Create a new eventTypeModel.
     *
     * @param eventTypeModelDTO the eventTypeModelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventTypeModelDTO, or with status {@code 400 (Bad Request)} if the eventTypeModel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-type-models")
    public ResponseEntity<EventTypeModelDTO> createEventTypeModel(@RequestBody EventTypeModelDTO eventTypeModelDTO)
        throws URISyntaxException {
        log.debug("REST request to save EventTypeModel : {}", eventTypeModelDTO);
        if (eventTypeModelDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventTypeModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventTypeModelDTO result = eventTypeModelService.save(eventTypeModelDTO);
        return ResponseEntity
            .created(new URI("/api/event-type-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-type-models/:id} : Updates an existing eventTypeModel.
     *
     * @param id the id of the eventTypeModelDTO to save.
     * @param eventTypeModelDTO the eventTypeModelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventTypeModelDTO,
     * or with status {@code 400 (Bad Request)} if the eventTypeModelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventTypeModelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-type-models/{id}")
    public ResponseEntity<EventTypeModelDTO> updateEventTypeModel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventTypeModelDTO eventTypeModelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventTypeModel : {}, {}", id, eventTypeModelDTO);
        if (eventTypeModelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventTypeModelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventTypeModelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventTypeModelDTO result = eventTypeModelService.update(eventTypeModelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventTypeModelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /event-type-models/:id} : Partial updates given fields of an existing eventTypeModel, field will ignore if it is null
     *
     * @param id the id of the eventTypeModelDTO to save.
     * @param eventTypeModelDTO the eventTypeModelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventTypeModelDTO,
     * or with status {@code 400 (Bad Request)} if the eventTypeModelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventTypeModelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventTypeModelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-type-models/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventTypeModelDTO> partialUpdateEventTypeModel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventTypeModelDTO eventTypeModelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventTypeModel partially : {}, {}", id, eventTypeModelDTO);
        if (eventTypeModelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventTypeModelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventTypeModelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventTypeModelDTO> result = eventTypeModelService.partialUpdate(eventTypeModelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventTypeModelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /event-type-models} : get all the eventTypeModels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventTypeModels in body.
     */
    @GetMapping("/event-type-models")
    public ResponseEntity<List<EventTypeModelDTO>> getAllEventTypeModels(
        EventTypeModelCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EventTypeModels by criteria: {}", criteria);
        Page<EventTypeModelDTO> page = eventTypeModelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /event-type-models/count} : count all the eventTypeModels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/event-type-models/count")
    public ResponseEntity<Long> countEventTypeModels(EventTypeModelCriteria criteria) {
        log.debug("REST request to count EventTypeModels by criteria: {}", criteria);
        return ResponseEntity.ok().body(eventTypeModelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /event-type-models/:id} : get the "id" eventTypeModel.
     *
     * @param id the id of the eventTypeModelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventTypeModelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-type-models/{id}")
    public ResponseEntity<EventTypeModelDTO> getEventTypeModel(@PathVariable Long id) {
        log.debug("REST request to get EventTypeModel : {}", id);
        Optional<EventTypeModelDTO> eventTypeModelDTO = eventTypeModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventTypeModelDTO);
    }

    /**
     * {@code DELETE  /event-type-models/:id} : delete the "id" eventTypeModel.
     *
     * @param id the id of the eventTypeModelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-type-models/{id}")
    public ResponseEntity<Void> deleteEventTypeModel(@PathVariable Long id) {
        log.debug("REST request to delete EventTypeModel : {}", id);
        eventTypeModelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
