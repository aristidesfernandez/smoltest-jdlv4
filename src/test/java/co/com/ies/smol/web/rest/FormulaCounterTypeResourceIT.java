package co.com.ies.smol.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.com.ies.smol.IntegrationTest;
import co.com.ies.smol.domain.CounterType;
import co.com.ies.smol.domain.Formula;
import co.com.ies.smol.domain.FormulaCounterType;
import co.com.ies.smol.repository.FormulaCounterTypeRepository;
import co.com.ies.smol.service.criteria.FormulaCounterTypeCriteria;
import co.com.ies.smol.service.dto.FormulaCounterTypeDTO;
import co.com.ies.smol.service.mapper.FormulaCounterTypeMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FormulaCounterTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormulaCounterTypeResourceIT {

    private static final String ENTITY_API_URL = "/api/formula-counter-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FormulaCounterTypeRepository formulaCounterTypeRepository;

    @Autowired
    private FormulaCounterTypeMapper formulaCounterTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormulaCounterTypeMockMvc;

    private FormulaCounterType formulaCounterType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormulaCounterType createEntity(EntityManager em) {
        FormulaCounterType formulaCounterType = new FormulaCounterType();
        return formulaCounterType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormulaCounterType createUpdatedEntity(EntityManager em) {
        FormulaCounterType formulaCounterType = new FormulaCounterType();
        return formulaCounterType;
    }

    @BeforeEach
    public void initTest() {
        formulaCounterType = createEntity(em);
    }

    @Test
    @Transactional
    void createFormulaCounterType() throws Exception {
        int databaseSizeBeforeCreate = formulaCounterTypeRepository.findAll().size();
        // Create the FormulaCounterType
        FormulaCounterTypeDTO formulaCounterTypeDTO = formulaCounterTypeMapper.toDto(formulaCounterType);
        restFormulaCounterTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formulaCounterTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FormulaCounterType in the database
        List<FormulaCounterType> formulaCounterTypeList = formulaCounterTypeRepository.findAll();
        assertThat(formulaCounterTypeList).hasSize(databaseSizeBeforeCreate + 1);
        FormulaCounterType testFormulaCounterType = formulaCounterTypeList.get(formulaCounterTypeList.size() - 1);
    }

    @Test
    @Transactional
    void createFormulaCounterTypeWithExistingId() throws Exception {
        // Create the FormulaCounterType with an existing ID
        formulaCounterType.setId(1L);
        FormulaCounterTypeDTO formulaCounterTypeDTO = formulaCounterTypeMapper.toDto(formulaCounterType);

        int databaseSizeBeforeCreate = formulaCounterTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormulaCounterTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formulaCounterTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormulaCounterType in the database
        List<FormulaCounterType> formulaCounterTypeList = formulaCounterTypeRepository.findAll();
        assertThat(formulaCounterTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormulaCounterTypes() throws Exception {
        // Initialize the database
        formulaCounterTypeRepository.saveAndFlush(formulaCounterType);

        // Get all the formulaCounterTypeList
        restFormulaCounterTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formulaCounterType.getId().intValue())));
    }

    @Test
    @Transactional
    void getFormulaCounterType() throws Exception {
        // Initialize the database
        formulaCounterTypeRepository.saveAndFlush(formulaCounterType);

        // Get the formulaCounterType
        restFormulaCounterTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, formulaCounterType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formulaCounterType.getId().intValue()));
    }

    @Test
    @Transactional
    void getFormulaCounterTypesByIdFiltering() throws Exception {
        // Initialize the database
        formulaCounterTypeRepository.saveAndFlush(formulaCounterType);

        Long id = formulaCounterType.getId();

        defaultFormulaCounterTypeShouldBeFound("id.equals=" + id);
        defaultFormulaCounterTypeShouldNotBeFound("id.notEquals=" + id);

        defaultFormulaCounterTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFormulaCounterTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultFormulaCounterTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFormulaCounterTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFormulaCounterTypesByFormulaIsEqualToSomething() throws Exception {
        Formula formula;
        if (TestUtil.findAll(em, Formula.class).isEmpty()) {
            formulaCounterTypeRepository.saveAndFlush(formulaCounterType);
            formula = FormulaResourceIT.createEntity(em);
        } else {
            formula = TestUtil.findAll(em, Formula.class).get(0);
        }
        em.persist(formula);
        em.flush();
        formulaCounterType.setFormula(formula);
        formulaCounterTypeRepository.saveAndFlush(formulaCounterType);
        Long formulaId = formula.getId();

        // Get all the formulaCounterTypeList where formula equals to formulaId
        defaultFormulaCounterTypeShouldBeFound("formulaId.equals=" + formulaId);

        // Get all the formulaCounterTypeList where formula equals to (formulaId + 1)
        defaultFormulaCounterTypeShouldNotBeFound("formulaId.equals=" + (formulaId + 1));
    }

    @Test
    @Transactional
    void getAllFormulaCounterTypesByCounterTypeIsEqualToSomething() throws Exception {
        CounterType counterType;
        if (TestUtil.findAll(em, CounterType.class).isEmpty()) {
            formulaCounterTypeRepository.saveAndFlush(formulaCounterType);
            counterType = CounterTypeResourceIT.createEntity(em);
        } else {
            counterType = TestUtil.findAll(em, CounterType.class).get(0);
        }
        em.persist(counterType);
        em.flush();
        formulaCounterType.setCounterType(counterType);
        formulaCounterTypeRepository.saveAndFlush(formulaCounterType);
        String counterTypeId = counterType.getCounterCode();

        // Get all the formulaCounterTypeList where counterType equals to counterTypeId
        defaultFormulaCounterTypeShouldBeFound("counterTypeId.equals=" + counterTypeId);

        // Get all the formulaCounterTypeList where counterType equals to "invalid-id"
        defaultFormulaCounterTypeShouldNotBeFound("counterTypeId.equals=" + "invalid-id");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFormulaCounterTypeShouldBeFound(String filter) throws Exception {
        restFormulaCounterTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formulaCounterType.getId().intValue())));

        // Check, that the count call also returns 1
        restFormulaCounterTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFormulaCounterTypeShouldNotBeFound(String filter) throws Exception {
        restFormulaCounterTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFormulaCounterTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFormulaCounterType() throws Exception {
        // Get the formulaCounterType
        restFormulaCounterTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFormulaCounterType() throws Exception {
        // Initialize the database
        formulaCounterTypeRepository.saveAndFlush(formulaCounterType);

        int databaseSizeBeforeUpdate = formulaCounterTypeRepository.findAll().size();

        // Update the formulaCounterType
        FormulaCounterType updatedFormulaCounterType = formulaCounterTypeRepository.findById(formulaCounterType.getId()).get();
        // Disconnect from session so that the updates on updatedFormulaCounterType are not directly saved in db
        em.detach(updatedFormulaCounterType);
        FormulaCounterTypeDTO formulaCounterTypeDTO = formulaCounterTypeMapper.toDto(updatedFormulaCounterType);

        restFormulaCounterTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formulaCounterTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formulaCounterTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the FormulaCounterType in the database
        List<FormulaCounterType> formulaCounterTypeList = formulaCounterTypeRepository.findAll();
        assertThat(formulaCounterTypeList).hasSize(databaseSizeBeforeUpdate);
        FormulaCounterType testFormulaCounterType = formulaCounterTypeList.get(formulaCounterTypeList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingFormulaCounterType() throws Exception {
        int databaseSizeBeforeUpdate = formulaCounterTypeRepository.findAll().size();
        formulaCounterType.setId(count.incrementAndGet());

        // Create the FormulaCounterType
        FormulaCounterTypeDTO formulaCounterTypeDTO = formulaCounterTypeMapper.toDto(formulaCounterType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormulaCounterTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formulaCounterTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formulaCounterTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormulaCounterType in the database
        List<FormulaCounterType> formulaCounterTypeList = formulaCounterTypeRepository.findAll();
        assertThat(formulaCounterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormulaCounterType() throws Exception {
        int databaseSizeBeforeUpdate = formulaCounterTypeRepository.findAll().size();
        formulaCounterType.setId(count.incrementAndGet());

        // Create the FormulaCounterType
        FormulaCounterTypeDTO formulaCounterTypeDTO = formulaCounterTypeMapper.toDto(formulaCounterType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormulaCounterTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formulaCounterTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormulaCounterType in the database
        List<FormulaCounterType> formulaCounterTypeList = formulaCounterTypeRepository.findAll();
        assertThat(formulaCounterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormulaCounterType() throws Exception {
        int databaseSizeBeforeUpdate = formulaCounterTypeRepository.findAll().size();
        formulaCounterType.setId(count.incrementAndGet());

        // Create the FormulaCounterType
        FormulaCounterTypeDTO formulaCounterTypeDTO = formulaCounterTypeMapper.toDto(formulaCounterType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormulaCounterTypeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formulaCounterTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormulaCounterType in the database
        List<FormulaCounterType> formulaCounterTypeList = formulaCounterTypeRepository.findAll();
        assertThat(formulaCounterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormulaCounterTypeWithPatch() throws Exception {
        // Initialize the database
        formulaCounterTypeRepository.saveAndFlush(formulaCounterType);

        int databaseSizeBeforeUpdate = formulaCounterTypeRepository.findAll().size();

        // Update the formulaCounterType using partial update
        FormulaCounterType partialUpdatedFormulaCounterType = new FormulaCounterType();
        partialUpdatedFormulaCounterType.setId(formulaCounterType.getId());

        restFormulaCounterTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormulaCounterType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormulaCounterType))
            )
            .andExpect(status().isOk());

        // Validate the FormulaCounterType in the database
        List<FormulaCounterType> formulaCounterTypeList = formulaCounterTypeRepository.findAll();
        assertThat(formulaCounterTypeList).hasSize(databaseSizeBeforeUpdate);
        FormulaCounterType testFormulaCounterType = formulaCounterTypeList.get(formulaCounterTypeList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateFormulaCounterTypeWithPatch() throws Exception {
        // Initialize the database
        formulaCounterTypeRepository.saveAndFlush(formulaCounterType);

        int databaseSizeBeforeUpdate = formulaCounterTypeRepository.findAll().size();

        // Update the formulaCounterType using partial update
        FormulaCounterType partialUpdatedFormulaCounterType = new FormulaCounterType();
        partialUpdatedFormulaCounterType.setId(formulaCounterType.getId());

        restFormulaCounterTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormulaCounterType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormulaCounterType))
            )
            .andExpect(status().isOk());

        // Validate the FormulaCounterType in the database
        List<FormulaCounterType> formulaCounterTypeList = formulaCounterTypeRepository.findAll();
        assertThat(formulaCounterTypeList).hasSize(databaseSizeBeforeUpdate);
        FormulaCounterType testFormulaCounterType = formulaCounterTypeList.get(formulaCounterTypeList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingFormulaCounterType() throws Exception {
        int databaseSizeBeforeUpdate = formulaCounterTypeRepository.findAll().size();
        formulaCounterType.setId(count.incrementAndGet());

        // Create the FormulaCounterType
        FormulaCounterTypeDTO formulaCounterTypeDTO = formulaCounterTypeMapper.toDto(formulaCounterType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormulaCounterTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formulaCounterTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formulaCounterTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormulaCounterType in the database
        List<FormulaCounterType> formulaCounterTypeList = formulaCounterTypeRepository.findAll();
        assertThat(formulaCounterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormulaCounterType() throws Exception {
        int databaseSizeBeforeUpdate = formulaCounterTypeRepository.findAll().size();
        formulaCounterType.setId(count.incrementAndGet());

        // Create the FormulaCounterType
        FormulaCounterTypeDTO formulaCounterTypeDTO = formulaCounterTypeMapper.toDto(formulaCounterType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormulaCounterTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formulaCounterTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormulaCounterType in the database
        List<FormulaCounterType> formulaCounterTypeList = formulaCounterTypeRepository.findAll();
        assertThat(formulaCounterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormulaCounterType() throws Exception {
        int databaseSizeBeforeUpdate = formulaCounterTypeRepository.findAll().size();
        formulaCounterType.setId(count.incrementAndGet());

        // Create the FormulaCounterType
        FormulaCounterTypeDTO formulaCounterTypeDTO = formulaCounterTypeMapper.toDto(formulaCounterType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormulaCounterTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formulaCounterTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormulaCounterType in the database
        List<FormulaCounterType> formulaCounterTypeList = formulaCounterTypeRepository.findAll();
        assertThat(formulaCounterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormulaCounterType() throws Exception {
        // Initialize the database
        formulaCounterTypeRepository.saveAndFlush(formulaCounterType);

        int databaseSizeBeforeDelete = formulaCounterTypeRepository.findAll().size();

        // Delete the formulaCounterType
        restFormulaCounterTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, formulaCounterType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormulaCounterType> formulaCounterTypeList = formulaCounterTypeRepository.findAll();
        assertThat(formulaCounterTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
