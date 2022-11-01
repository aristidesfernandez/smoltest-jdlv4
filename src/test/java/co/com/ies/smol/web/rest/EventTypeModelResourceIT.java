package co.com.ies.smol.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.com.ies.smol.IntegrationTest;
import co.com.ies.smol.domain.EventType;
import co.com.ies.smol.domain.EventTypeModel;
import co.com.ies.smol.repository.EventTypeModelRepository;
import co.com.ies.smol.service.criteria.EventTypeModelCriteria;
import co.com.ies.smol.service.dto.EventTypeModelDTO;
import co.com.ies.smol.service.mapper.EventTypeModelMapper;
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
 * Integration tests for the {@link EventTypeModelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventTypeModelResourceIT {

    private static final Integer DEFAULT_MODEL_ID = 1;
    private static final Integer UPDATED_MODEL_ID = 2;
    private static final Integer SMALLER_MODEL_ID = 1 - 1;

    private static final String ENTITY_API_URL = "/api/event-type-models";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventTypeModelRepository eventTypeModelRepository;

    @Autowired
    private EventTypeModelMapper eventTypeModelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventTypeModelMockMvc;

    private EventTypeModel eventTypeModel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventTypeModel createEntity(EntityManager em) {
        EventTypeModel eventTypeModel = new EventTypeModel().modelId(DEFAULT_MODEL_ID);
        return eventTypeModel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventTypeModel createUpdatedEntity(EntityManager em) {
        EventTypeModel eventTypeModel = new EventTypeModel().modelId(UPDATED_MODEL_ID);
        return eventTypeModel;
    }

    @BeforeEach
    public void initTest() {
        eventTypeModel = createEntity(em);
    }

    @Test
    @Transactional
    void createEventTypeModel() throws Exception {
        int databaseSizeBeforeCreate = eventTypeModelRepository.findAll().size();
        // Create the EventTypeModel
        EventTypeModelDTO eventTypeModelDTO = eventTypeModelMapper.toDto(eventTypeModel);
        restEventTypeModelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventTypeModelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EventTypeModel in the database
        List<EventTypeModel> eventTypeModelList = eventTypeModelRepository.findAll();
        assertThat(eventTypeModelList).hasSize(databaseSizeBeforeCreate + 1);
        EventTypeModel testEventTypeModel = eventTypeModelList.get(eventTypeModelList.size() - 1);
        assertThat(testEventTypeModel.getModelId()).isEqualTo(DEFAULT_MODEL_ID);
    }

    @Test
    @Transactional
    void createEventTypeModelWithExistingId() throws Exception {
        // Create the EventTypeModel with an existing ID
        eventTypeModel.setId(1L);
        EventTypeModelDTO eventTypeModelDTO = eventTypeModelMapper.toDto(eventTypeModel);

        int databaseSizeBeforeCreate = eventTypeModelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventTypeModelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventTypeModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventTypeModel in the database
        List<EventTypeModel> eventTypeModelList = eventTypeModelRepository.findAll();
        assertThat(eventTypeModelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEventTypeModels() throws Exception {
        // Initialize the database
        eventTypeModelRepository.saveAndFlush(eventTypeModel);

        // Get all the eventTypeModelList
        restEventTypeModelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventTypeModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].modelId").value(hasItem(DEFAULT_MODEL_ID)));
    }

    @Test
    @Transactional
    void getEventTypeModel() throws Exception {
        // Initialize the database
        eventTypeModelRepository.saveAndFlush(eventTypeModel);

        // Get the eventTypeModel
        restEventTypeModelMockMvc
            .perform(get(ENTITY_API_URL_ID, eventTypeModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventTypeModel.getId().intValue()))
            .andExpect(jsonPath("$.modelId").value(DEFAULT_MODEL_ID));
    }

    @Test
    @Transactional
    void getEventTypeModelsByIdFiltering() throws Exception {
        // Initialize the database
        eventTypeModelRepository.saveAndFlush(eventTypeModel);

        Long id = eventTypeModel.getId();

        defaultEventTypeModelShouldBeFound("id.equals=" + id);
        defaultEventTypeModelShouldNotBeFound("id.notEquals=" + id);

        defaultEventTypeModelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEventTypeModelShouldNotBeFound("id.greaterThan=" + id);

        defaultEventTypeModelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEventTypeModelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEventTypeModelsByModelIdIsEqualToSomething() throws Exception {
        // Initialize the database
        eventTypeModelRepository.saveAndFlush(eventTypeModel);

        // Get all the eventTypeModelList where modelId equals to DEFAULT_MODEL_ID
        defaultEventTypeModelShouldBeFound("modelId.equals=" + DEFAULT_MODEL_ID);

        // Get all the eventTypeModelList where modelId equals to UPDATED_MODEL_ID
        defaultEventTypeModelShouldNotBeFound("modelId.equals=" + UPDATED_MODEL_ID);
    }

    @Test
    @Transactional
    void getAllEventTypeModelsByModelIdIsInShouldWork() throws Exception {
        // Initialize the database
        eventTypeModelRepository.saveAndFlush(eventTypeModel);

        // Get all the eventTypeModelList where modelId in DEFAULT_MODEL_ID or UPDATED_MODEL_ID
        defaultEventTypeModelShouldBeFound("modelId.in=" + DEFAULT_MODEL_ID + "," + UPDATED_MODEL_ID);

        // Get all the eventTypeModelList where modelId equals to UPDATED_MODEL_ID
        defaultEventTypeModelShouldNotBeFound("modelId.in=" + UPDATED_MODEL_ID);
    }

    @Test
    @Transactional
    void getAllEventTypeModelsByModelIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventTypeModelRepository.saveAndFlush(eventTypeModel);

        // Get all the eventTypeModelList where modelId is not null
        defaultEventTypeModelShouldBeFound("modelId.specified=true");

        // Get all the eventTypeModelList where modelId is null
        defaultEventTypeModelShouldNotBeFound("modelId.specified=false");
    }

    @Test
    @Transactional
    void getAllEventTypeModelsByModelIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventTypeModelRepository.saveAndFlush(eventTypeModel);

        // Get all the eventTypeModelList where modelId is greater than or equal to DEFAULT_MODEL_ID
        defaultEventTypeModelShouldBeFound("modelId.greaterThanOrEqual=" + DEFAULT_MODEL_ID);

        // Get all the eventTypeModelList where modelId is greater than or equal to UPDATED_MODEL_ID
        defaultEventTypeModelShouldNotBeFound("modelId.greaterThanOrEqual=" + UPDATED_MODEL_ID);
    }

    @Test
    @Transactional
    void getAllEventTypeModelsByModelIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventTypeModelRepository.saveAndFlush(eventTypeModel);

        // Get all the eventTypeModelList where modelId is less than or equal to DEFAULT_MODEL_ID
        defaultEventTypeModelShouldBeFound("modelId.lessThanOrEqual=" + DEFAULT_MODEL_ID);

        // Get all the eventTypeModelList where modelId is less than or equal to SMALLER_MODEL_ID
        defaultEventTypeModelShouldNotBeFound("modelId.lessThanOrEqual=" + SMALLER_MODEL_ID);
    }

    @Test
    @Transactional
    void getAllEventTypeModelsByModelIdIsLessThanSomething() throws Exception {
        // Initialize the database
        eventTypeModelRepository.saveAndFlush(eventTypeModel);

        // Get all the eventTypeModelList where modelId is less than DEFAULT_MODEL_ID
        defaultEventTypeModelShouldNotBeFound("modelId.lessThan=" + DEFAULT_MODEL_ID);

        // Get all the eventTypeModelList where modelId is less than UPDATED_MODEL_ID
        defaultEventTypeModelShouldBeFound("modelId.lessThan=" + UPDATED_MODEL_ID);
    }

    @Test
    @Transactional
    void getAllEventTypeModelsByModelIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventTypeModelRepository.saveAndFlush(eventTypeModel);

        // Get all the eventTypeModelList where modelId is greater than DEFAULT_MODEL_ID
        defaultEventTypeModelShouldNotBeFound("modelId.greaterThan=" + DEFAULT_MODEL_ID);

        // Get all the eventTypeModelList where modelId is greater than SMALLER_MODEL_ID
        defaultEventTypeModelShouldBeFound("modelId.greaterThan=" + SMALLER_MODEL_ID);
    }

    @Test
    @Transactional
    void getAllEventTypeModelsByEventTypeIsEqualToSomething() throws Exception {
        EventType eventType;
        if (TestUtil.findAll(em, EventType.class).isEmpty()) {
            eventTypeModelRepository.saveAndFlush(eventTypeModel);
            eventType = EventTypeResourceIT.createEntity(em);
        } else {
            eventType = TestUtil.findAll(em, EventType.class).get(0);
        }
        em.persist(eventType);
        em.flush();
        eventTypeModel.setEventType(eventType);
        eventTypeModelRepository.saveAndFlush(eventTypeModel);
        Long eventTypeId = eventType.getId();

        // Get all the eventTypeModelList where eventType equals to eventTypeId
        defaultEventTypeModelShouldBeFound("eventTypeId.equals=" + eventTypeId);

        // Get all the eventTypeModelList where eventType equals to (eventTypeId + 1)
        defaultEventTypeModelShouldNotBeFound("eventTypeId.equals=" + (eventTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEventTypeModelShouldBeFound(String filter) throws Exception {
        restEventTypeModelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventTypeModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].modelId").value(hasItem(DEFAULT_MODEL_ID)));

        // Check, that the count call also returns 1
        restEventTypeModelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEventTypeModelShouldNotBeFound(String filter) throws Exception {
        restEventTypeModelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEventTypeModelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEventTypeModel() throws Exception {
        // Get the eventTypeModel
        restEventTypeModelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventTypeModel() throws Exception {
        // Initialize the database
        eventTypeModelRepository.saveAndFlush(eventTypeModel);

        int databaseSizeBeforeUpdate = eventTypeModelRepository.findAll().size();

        // Update the eventTypeModel
        EventTypeModel updatedEventTypeModel = eventTypeModelRepository.findById(eventTypeModel.getId()).get();
        // Disconnect from session so that the updates on updatedEventTypeModel are not directly saved in db
        em.detach(updatedEventTypeModel);
        updatedEventTypeModel.modelId(UPDATED_MODEL_ID);
        EventTypeModelDTO eventTypeModelDTO = eventTypeModelMapper.toDto(updatedEventTypeModel);

        restEventTypeModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventTypeModelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventTypeModelDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventTypeModel in the database
        List<EventTypeModel> eventTypeModelList = eventTypeModelRepository.findAll();
        assertThat(eventTypeModelList).hasSize(databaseSizeBeforeUpdate);
        EventTypeModel testEventTypeModel = eventTypeModelList.get(eventTypeModelList.size() - 1);
        assertThat(testEventTypeModel.getModelId()).isEqualTo(UPDATED_MODEL_ID);
    }

    @Test
    @Transactional
    void putNonExistingEventTypeModel() throws Exception {
        int databaseSizeBeforeUpdate = eventTypeModelRepository.findAll().size();
        eventTypeModel.setId(count.incrementAndGet());

        // Create the EventTypeModel
        EventTypeModelDTO eventTypeModelDTO = eventTypeModelMapper.toDto(eventTypeModel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventTypeModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventTypeModelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventTypeModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventTypeModel in the database
        List<EventTypeModel> eventTypeModelList = eventTypeModelRepository.findAll();
        assertThat(eventTypeModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventTypeModel() throws Exception {
        int databaseSizeBeforeUpdate = eventTypeModelRepository.findAll().size();
        eventTypeModel.setId(count.incrementAndGet());

        // Create the EventTypeModel
        EventTypeModelDTO eventTypeModelDTO = eventTypeModelMapper.toDto(eventTypeModel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventTypeModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventTypeModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventTypeModel in the database
        List<EventTypeModel> eventTypeModelList = eventTypeModelRepository.findAll();
        assertThat(eventTypeModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventTypeModel() throws Exception {
        int databaseSizeBeforeUpdate = eventTypeModelRepository.findAll().size();
        eventTypeModel.setId(count.incrementAndGet());

        // Create the EventTypeModel
        EventTypeModelDTO eventTypeModelDTO = eventTypeModelMapper.toDto(eventTypeModel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventTypeModelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventTypeModelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventTypeModel in the database
        List<EventTypeModel> eventTypeModelList = eventTypeModelRepository.findAll();
        assertThat(eventTypeModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventTypeModelWithPatch() throws Exception {
        // Initialize the database
        eventTypeModelRepository.saveAndFlush(eventTypeModel);

        int databaseSizeBeforeUpdate = eventTypeModelRepository.findAll().size();

        // Update the eventTypeModel using partial update
        EventTypeModel partialUpdatedEventTypeModel = new EventTypeModel();
        partialUpdatedEventTypeModel.setId(eventTypeModel.getId());

        restEventTypeModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventTypeModel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventTypeModel))
            )
            .andExpect(status().isOk());

        // Validate the EventTypeModel in the database
        List<EventTypeModel> eventTypeModelList = eventTypeModelRepository.findAll();
        assertThat(eventTypeModelList).hasSize(databaseSizeBeforeUpdate);
        EventTypeModel testEventTypeModel = eventTypeModelList.get(eventTypeModelList.size() - 1);
        assertThat(testEventTypeModel.getModelId()).isEqualTo(DEFAULT_MODEL_ID);
    }

    @Test
    @Transactional
    void fullUpdateEventTypeModelWithPatch() throws Exception {
        // Initialize the database
        eventTypeModelRepository.saveAndFlush(eventTypeModel);

        int databaseSizeBeforeUpdate = eventTypeModelRepository.findAll().size();

        // Update the eventTypeModel using partial update
        EventTypeModel partialUpdatedEventTypeModel = new EventTypeModel();
        partialUpdatedEventTypeModel.setId(eventTypeModel.getId());

        partialUpdatedEventTypeModel.modelId(UPDATED_MODEL_ID);

        restEventTypeModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventTypeModel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventTypeModel))
            )
            .andExpect(status().isOk());

        // Validate the EventTypeModel in the database
        List<EventTypeModel> eventTypeModelList = eventTypeModelRepository.findAll();
        assertThat(eventTypeModelList).hasSize(databaseSizeBeforeUpdate);
        EventTypeModel testEventTypeModel = eventTypeModelList.get(eventTypeModelList.size() - 1);
        assertThat(testEventTypeModel.getModelId()).isEqualTo(UPDATED_MODEL_ID);
    }

    @Test
    @Transactional
    void patchNonExistingEventTypeModel() throws Exception {
        int databaseSizeBeforeUpdate = eventTypeModelRepository.findAll().size();
        eventTypeModel.setId(count.incrementAndGet());

        // Create the EventTypeModel
        EventTypeModelDTO eventTypeModelDTO = eventTypeModelMapper.toDto(eventTypeModel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventTypeModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventTypeModelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventTypeModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventTypeModel in the database
        List<EventTypeModel> eventTypeModelList = eventTypeModelRepository.findAll();
        assertThat(eventTypeModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventTypeModel() throws Exception {
        int databaseSizeBeforeUpdate = eventTypeModelRepository.findAll().size();
        eventTypeModel.setId(count.incrementAndGet());

        // Create the EventTypeModel
        EventTypeModelDTO eventTypeModelDTO = eventTypeModelMapper.toDto(eventTypeModel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventTypeModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventTypeModelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventTypeModel in the database
        List<EventTypeModel> eventTypeModelList = eventTypeModelRepository.findAll();
        assertThat(eventTypeModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventTypeModel() throws Exception {
        int databaseSizeBeforeUpdate = eventTypeModelRepository.findAll().size();
        eventTypeModel.setId(count.incrementAndGet());

        // Create the EventTypeModel
        EventTypeModelDTO eventTypeModelDTO = eventTypeModelMapper.toDto(eventTypeModel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventTypeModelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventTypeModelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventTypeModel in the database
        List<EventTypeModel> eventTypeModelList = eventTypeModelRepository.findAll();
        assertThat(eventTypeModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventTypeModel() throws Exception {
        // Initialize the database
        eventTypeModelRepository.saveAndFlush(eventTypeModel);

        int databaseSizeBeforeDelete = eventTypeModelRepository.findAll().size();

        // Delete the eventTypeModel
        restEventTypeModelMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventTypeModel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventTypeModel> eventTypeModelList = eventTypeModelRepository.findAll();
        assertThat(eventTypeModelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
