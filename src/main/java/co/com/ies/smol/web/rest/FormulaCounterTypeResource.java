package co.com.ies.smol.web.rest;

import co.com.ies.smol.repository.FormulaCounterTypeRepository;
import co.com.ies.smol.service.FormulaCounterTypeQueryService;
import co.com.ies.smol.service.FormulaCounterTypeService;
import co.com.ies.smol.service.criteria.FormulaCounterTypeCriteria;
import co.com.ies.smol.service.dto.FormulaCounterTypeDTO;
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
 * REST controller for managing {@link co.com.ies.smol.domain.FormulaCounterType}.
 */
@RestController
@RequestMapping("/api")
public class FormulaCounterTypeResource {

    private final Logger log = LoggerFactory.getLogger(FormulaCounterTypeResource.class);

    private static final String ENTITY_NAME = "formulaCounterType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormulaCounterTypeService formulaCounterTypeService;

    private final FormulaCounterTypeRepository formulaCounterTypeRepository;

    private final FormulaCounterTypeQueryService formulaCounterTypeQueryService;

    public FormulaCounterTypeResource(
        FormulaCounterTypeService formulaCounterTypeService,
        FormulaCounterTypeRepository formulaCounterTypeRepository,
        FormulaCounterTypeQueryService formulaCounterTypeQueryService
    ) {
        this.formulaCounterTypeService = formulaCounterTypeService;
        this.formulaCounterTypeRepository = formulaCounterTypeRepository;
        this.formulaCounterTypeQueryService = formulaCounterTypeQueryService;
    }

    /**
     * {@code POST  /formula-counter-types} : Create a new formulaCounterType.
     *
     * @param formulaCounterTypeDTO the formulaCounterTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formulaCounterTypeDTO, or with status {@code 400 (Bad Request)} if the formulaCounterType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/formula-counter-types")
    public ResponseEntity<FormulaCounterTypeDTO> createFormulaCounterType(@RequestBody FormulaCounterTypeDTO formulaCounterTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save FormulaCounterType : {}", formulaCounterTypeDTO);
        if (formulaCounterTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new formulaCounterType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormulaCounterTypeDTO result = formulaCounterTypeService.save(formulaCounterTypeDTO);
        return ResponseEntity
            .created(new URI("/api/formula-counter-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /formula-counter-types/:id} : Updates an existing formulaCounterType.
     *
     * @param id the id of the formulaCounterTypeDTO to save.
     * @param formulaCounterTypeDTO the formulaCounterTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formulaCounterTypeDTO,
     * or with status {@code 400 (Bad Request)} if the formulaCounterTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formulaCounterTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/formula-counter-types/{id}")
    public ResponseEntity<FormulaCounterTypeDTO> updateFormulaCounterType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormulaCounterTypeDTO formulaCounterTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FormulaCounterType : {}, {}", id, formulaCounterTypeDTO);
        if (formulaCounterTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formulaCounterTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formulaCounterTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FormulaCounterTypeDTO result = formulaCounterTypeService.update(formulaCounterTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formulaCounterTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /formula-counter-types/:id} : Partial updates given fields of an existing formulaCounterType, field will ignore if it is null
     *
     * @param id the id of the formulaCounterTypeDTO to save.
     * @param formulaCounterTypeDTO the formulaCounterTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formulaCounterTypeDTO,
     * or with status {@code 400 (Bad Request)} if the formulaCounterTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the formulaCounterTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the formulaCounterTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/formula-counter-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FormulaCounterTypeDTO> partialUpdateFormulaCounterType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormulaCounterTypeDTO formulaCounterTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FormulaCounterType partially : {}, {}", id, formulaCounterTypeDTO);
        if (formulaCounterTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formulaCounterTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formulaCounterTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FormulaCounterTypeDTO> result = formulaCounterTypeService.partialUpdate(formulaCounterTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formulaCounterTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /formula-counter-types} : get all the formulaCounterTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formulaCounterTypes in body.
     */
    @GetMapping("/formula-counter-types")
    public ResponseEntity<List<FormulaCounterTypeDTO>> getAllFormulaCounterTypes(
        FormulaCounterTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FormulaCounterTypes by criteria: {}", criteria);
        Page<FormulaCounterTypeDTO> page = formulaCounterTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /formula-counter-types/count} : count all the formulaCounterTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/formula-counter-types/count")
    public ResponseEntity<Long> countFormulaCounterTypes(FormulaCounterTypeCriteria criteria) {
        log.debug("REST request to count FormulaCounterTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(formulaCounterTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /formula-counter-types/:id} : get the "id" formulaCounterType.
     *
     * @param id the id of the formulaCounterTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formulaCounterTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/formula-counter-types/{id}")
    public ResponseEntity<FormulaCounterTypeDTO> getFormulaCounterType(@PathVariable Long id) {
        log.debug("REST request to get FormulaCounterType : {}", id);
        Optional<FormulaCounterTypeDTO> formulaCounterTypeDTO = formulaCounterTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formulaCounterTypeDTO);
    }

    /**
     * {@code DELETE  /formula-counter-types/:id} : delete the "id" formulaCounterType.
     *
     * @param id the id of the formulaCounterTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/formula-counter-types/{id}")
    public ResponseEntity<Void> deleteFormulaCounterType(@PathVariable Long id) {
        log.debug("REST request to delete FormulaCounterType : {}", id);
        formulaCounterTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
