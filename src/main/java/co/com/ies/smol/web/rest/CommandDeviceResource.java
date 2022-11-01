package co.com.ies.smol.web.rest;

import co.com.ies.smol.repository.CommandDeviceRepository;
import co.com.ies.smol.service.CommandDeviceQueryService;
import co.com.ies.smol.service.CommandDeviceService;
import co.com.ies.smol.service.criteria.CommandDeviceCriteria;
import co.com.ies.smol.service.dto.CommandDeviceDTO;
import co.com.ies.smol.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link co.com.ies.smol.domain.CommandDevice}.
 */
@RestController
@RequestMapping("/api")
public class CommandDeviceResource {

    private final Logger log = LoggerFactory.getLogger(CommandDeviceResource.class);

    private static final String ENTITY_NAME = "commandDevice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommandDeviceService commandDeviceService;

    private final CommandDeviceRepository commandDeviceRepository;

    private final CommandDeviceQueryService commandDeviceQueryService;

    public CommandDeviceResource(
        CommandDeviceService commandDeviceService,
        CommandDeviceRepository commandDeviceRepository,
        CommandDeviceQueryService commandDeviceQueryService
    ) {
        this.commandDeviceService = commandDeviceService;
        this.commandDeviceRepository = commandDeviceRepository;
        this.commandDeviceQueryService = commandDeviceQueryService;
    }

    /**
     * {@code POST  /command-devices} : Create a new commandDevice.
     *
     * @param commandDeviceDTO the commandDeviceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commandDeviceDTO, or with status {@code 400 (Bad Request)} if the commandDevice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/command-devices")
    public ResponseEntity<CommandDeviceDTO> createCommandDevice(@Valid @RequestBody CommandDeviceDTO commandDeviceDTO)
        throws URISyntaxException {
        log.debug("REST request to save CommandDevice : {}", commandDeviceDTO);
        if (commandDeviceDTO.getId() != null) {
            throw new BadRequestAlertException("A new commandDevice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommandDeviceDTO result = commandDeviceService.save(commandDeviceDTO);
        return ResponseEntity
            .created(new URI("/api/command-devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /command-devices/:id} : Updates an existing commandDevice.
     *
     * @param id the id of the commandDeviceDTO to save.
     * @param commandDeviceDTO the commandDeviceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandDeviceDTO,
     * or with status {@code 400 (Bad Request)} if the commandDeviceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commandDeviceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/command-devices/{id}")
    public ResponseEntity<CommandDeviceDTO> updateCommandDevice(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CommandDeviceDTO commandDeviceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CommandDevice : {}, {}", id, commandDeviceDTO);
        if (commandDeviceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commandDeviceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commandDeviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommandDeviceDTO result = commandDeviceService.update(commandDeviceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commandDeviceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /command-devices/:id} : Partial updates given fields of an existing commandDevice, field will ignore if it is null
     *
     * @param id the id of the commandDeviceDTO to save.
     * @param commandDeviceDTO the commandDeviceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandDeviceDTO,
     * or with status {@code 400 (Bad Request)} if the commandDeviceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the commandDeviceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the commandDeviceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/command-devices/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommandDeviceDTO> partialUpdateCommandDevice(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CommandDeviceDTO commandDeviceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CommandDevice partially : {}, {}", id, commandDeviceDTO);
        if (commandDeviceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commandDeviceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commandDeviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommandDeviceDTO> result = commandDeviceService.partialUpdate(commandDeviceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commandDeviceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /command-devices} : get all the commandDevices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commandDevices in body.
     */
    @GetMapping("/command-devices")
    public ResponseEntity<List<CommandDeviceDTO>> getAllCommandDevices(
        CommandDeviceCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CommandDevices by criteria: {}", criteria);
        Page<CommandDeviceDTO> page = commandDeviceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /command-devices/count} : count all the commandDevices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/command-devices/count")
    public ResponseEntity<Long> countCommandDevices(CommandDeviceCriteria criteria) {
        log.debug("REST request to count CommandDevices by criteria: {}", criteria);
        return ResponseEntity.ok().body(commandDeviceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /command-devices/:id} : get the "id" commandDevice.
     *
     * @param id the id of the commandDeviceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commandDeviceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/command-devices/{id}")
    public ResponseEntity<CommandDeviceDTO> getCommandDevice(@PathVariable Long id) {
        log.debug("REST request to get CommandDevice : {}", id);
        Optional<CommandDeviceDTO> commandDeviceDTO = commandDeviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commandDeviceDTO);
    }

    /**
     * {@code DELETE  /command-devices/:id} : delete the "id" commandDevice.
     *
     * @param id the id of the commandDeviceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/command-devices/{id}")
    public ResponseEntity<Void> deleteCommandDevice(@PathVariable Long id) {
        log.debug("REST request to delete CommandDevice : {}", id);
        commandDeviceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
