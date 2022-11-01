package co.com.ies.smol.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.com.ies.smol.IntegrationTest;
import co.com.ies.smol.domain.Command;
import co.com.ies.smol.domain.CommandDevice;
import co.com.ies.smol.domain.Device;
import co.com.ies.smol.repository.CommandDeviceRepository;
import co.com.ies.smol.service.criteria.CommandDeviceCriteria;
import co.com.ies.smol.service.dto.CommandDeviceDTO;
import co.com.ies.smol.service.mapper.CommandDeviceMapper;
import java.util.List;
import java.util.Random;
import java.util.UUID;
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
 * Integration tests for the {@link CommandDeviceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommandDeviceResourceIT {

    private static final String ENTITY_API_URL = "/api/command-devices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CommandDeviceRepository commandDeviceRepository;

    @Autowired
    private CommandDeviceMapper commandDeviceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommandDeviceMockMvc;

    private CommandDevice commandDevice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandDevice createEntity(EntityManager em) {
        CommandDevice commandDevice = new CommandDevice();
        // Add required entity
        Command command;
        if (TestUtil.findAll(em, Command.class).isEmpty()) {
            command = CommandResourceIT.createEntity(em);
            em.persist(command);
            em.flush();
        } else {
            command = TestUtil.findAll(em, Command.class).get(0);
        }
        commandDevice.setCommand(command);
        // Add required entity
        Device device;
        if (TestUtil.findAll(em, Device.class).isEmpty()) {
            device = DeviceResourceIT.createEntity(em);
            em.persist(device);
            em.flush();
        } else {
            device = TestUtil.findAll(em, Device.class).get(0);
        }
        commandDevice.setDevice(device);
        return commandDevice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandDevice createUpdatedEntity(EntityManager em) {
        CommandDevice commandDevice = new CommandDevice();
        // Add required entity
        Command command;
        if (TestUtil.findAll(em, Command.class).isEmpty()) {
            command = CommandResourceIT.createUpdatedEntity(em);
            em.persist(command);
            em.flush();
        } else {
            command = TestUtil.findAll(em, Command.class).get(0);
        }
        commandDevice.setCommand(command);
        // Add required entity
        Device device;
        if (TestUtil.findAll(em, Device.class).isEmpty()) {
            device = DeviceResourceIT.createUpdatedEntity(em);
            em.persist(device);
            em.flush();
        } else {
            device = TestUtil.findAll(em, Device.class).get(0);
        }
        commandDevice.setDevice(device);
        return commandDevice;
    }

    @BeforeEach
    public void initTest() {
        commandDevice = createEntity(em);
    }

    @Test
    @Transactional
    void createCommandDevice() throws Exception {
        int databaseSizeBeforeCreate = commandDeviceRepository.findAll().size();
        // Create the CommandDevice
        CommandDeviceDTO commandDeviceDTO = commandDeviceMapper.toDto(commandDevice);
        restCommandDeviceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commandDeviceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CommandDevice in the database
        List<CommandDevice> commandDeviceList = commandDeviceRepository.findAll();
        assertThat(commandDeviceList).hasSize(databaseSizeBeforeCreate + 1);
        CommandDevice testCommandDevice = commandDeviceList.get(commandDeviceList.size() - 1);
    }

    @Test
    @Transactional
    void createCommandDeviceWithExistingId() throws Exception {
        // Create the CommandDevice with an existing ID
        commandDevice.setId(1L);
        CommandDeviceDTO commandDeviceDTO = commandDeviceMapper.toDto(commandDevice);

        int databaseSizeBeforeCreate = commandDeviceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandDeviceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commandDeviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommandDevice in the database
        List<CommandDevice> commandDeviceList = commandDeviceRepository.findAll();
        assertThat(commandDeviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCommandDevices() throws Exception {
        // Initialize the database
        commandDeviceRepository.saveAndFlush(commandDevice);

        // Get all the commandDeviceList
        restCommandDeviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandDevice.getId().intValue())));
    }

    @Test
    @Transactional
    void getCommandDevice() throws Exception {
        // Initialize the database
        commandDeviceRepository.saveAndFlush(commandDevice);

        // Get the commandDevice
        restCommandDeviceMockMvc
            .perform(get(ENTITY_API_URL_ID, commandDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commandDevice.getId().intValue()));
    }

    @Test
    @Transactional
    void getCommandDevicesByIdFiltering() throws Exception {
        // Initialize the database
        commandDeviceRepository.saveAndFlush(commandDevice);

        Long id = commandDevice.getId();

        defaultCommandDeviceShouldBeFound("id.equals=" + id);
        defaultCommandDeviceShouldNotBeFound("id.notEquals=" + id);

        defaultCommandDeviceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCommandDeviceShouldNotBeFound("id.greaterThan=" + id);

        defaultCommandDeviceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCommandDeviceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCommandDevicesByCommandIsEqualToSomething() throws Exception {
        Command command;
        if (TestUtil.findAll(em, Command.class).isEmpty()) {
            commandDeviceRepository.saveAndFlush(commandDevice);
            command = CommandResourceIT.createEntity(em);
        } else {
            command = TestUtil.findAll(em, Command.class).get(0);
        }
        em.persist(command);
        em.flush();
        commandDevice.setCommand(command);
        commandDeviceRepository.saveAndFlush(commandDevice);
        Long commandId = command.getId();

        // Get all the commandDeviceList where command equals to commandId
        defaultCommandDeviceShouldBeFound("commandId.equals=" + commandId);

        // Get all the commandDeviceList where command equals to (commandId + 1)
        defaultCommandDeviceShouldNotBeFound("commandId.equals=" + (commandId + 1));
    }

    @Test
    @Transactional
    void getAllCommandDevicesByDeviceIsEqualToSomething() throws Exception {
        Device device;
        if (TestUtil.findAll(em, Device.class).isEmpty()) {
            commandDeviceRepository.saveAndFlush(commandDevice);
            device = DeviceResourceIT.createEntity(em);
        } else {
            device = TestUtil.findAll(em, Device.class).get(0);
        }
        em.persist(device);
        em.flush();
        commandDevice.setDevice(device);
        commandDeviceRepository.saveAndFlush(commandDevice);
        UUID deviceId = device.getId();

        // Get all the commandDeviceList where device equals to deviceId
        defaultCommandDeviceShouldBeFound("deviceId.equals=" + deviceId);

        // Get all the commandDeviceList where device equals to UUID.randomUUID()
        defaultCommandDeviceShouldNotBeFound("deviceId.equals=" + UUID.randomUUID());
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommandDeviceShouldBeFound(String filter) throws Exception {
        restCommandDeviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandDevice.getId().intValue())));

        // Check, that the count call also returns 1
        restCommandDeviceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommandDeviceShouldNotBeFound(String filter) throws Exception {
        restCommandDeviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommandDeviceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCommandDevice() throws Exception {
        // Get the commandDevice
        restCommandDeviceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCommandDevice() throws Exception {
        // Initialize the database
        commandDeviceRepository.saveAndFlush(commandDevice);

        int databaseSizeBeforeUpdate = commandDeviceRepository.findAll().size();

        // Update the commandDevice
        CommandDevice updatedCommandDevice = commandDeviceRepository.findById(commandDevice.getId()).get();
        // Disconnect from session so that the updates on updatedCommandDevice are not directly saved in db
        em.detach(updatedCommandDevice);
        CommandDeviceDTO commandDeviceDTO = commandDeviceMapper.toDto(updatedCommandDevice);

        restCommandDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commandDeviceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commandDeviceDTO))
            )
            .andExpect(status().isOk());

        // Validate the CommandDevice in the database
        List<CommandDevice> commandDeviceList = commandDeviceRepository.findAll();
        assertThat(commandDeviceList).hasSize(databaseSizeBeforeUpdate);
        CommandDevice testCommandDevice = commandDeviceList.get(commandDeviceList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingCommandDevice() throws Exception {
        int databaseSizeBeforeUpdate = commandDeviceRepository.findAll().size();
        commandDevice.setId(count.incrementAndGet());

        // Create the CommandDevice
        CommandDeviceDTO commandDeviceDTO = commandDeviceMapper.toDto(commandDevice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commandDeviceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commandDeviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommandDevice in the database
        List<CommandDevice> commandDeviceList = commandDeviceRepository.findAll();
        assertThat(commandDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommandDevice() throws Exception {
        int databaseSizeBeforeUpdate = commandDeviceRepository.findAll().size();
        commandDevice.setId(count.incrementAndGet());

        // Create the CommandDevice
        CommandDeviceDTO commandDeviceDTO = commandDeviceMapper.toDto(commandDevice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommandDeviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commandDeviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommandDevice in the database
        List<CommandDevice> commandDeviceList = commandDeviceRepository.findAll();
        assertThat(commandDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommandDevice() throws Exception {
        int databaseSizeBeforeUpdate = commandDeviceRepository.findAll().size();
        commandDevice.setId(count.incrementAndGet());

        // Create the CommandDevice
        CommandDeviceDTO commandDeviceDTO = commandDeviceMapper.toDto(commandDevice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommandDeviceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commandDeviceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommandDevice in the database
        List<CommandDevice> commandDeviceList = commandDeviceRepository.findAll();
        assertThat(commandDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommandDeviceWithPatch() throws Exception {
        // Initialize the database
        commandDeviceRepository.saveAndFlush(commandDevice);

        int databaseSizeBeforeUpdate = commandDeviceRepository.findAll().size();

        // Update the commandDevice using partial update
        CommandDevice partialUpdatedCommandDevice = new CommandDevice();
        partialUpdatedCommandDevice.setId(commandDevice.getId());

        restCommandDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommandDevice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommandDevice))
            )
            .andExpect(status().isOk());

        // Validate the CommandDevice in the database
        List<CommandDevice> commandDeviceList = commandDeviceRepository.findAll();
        assertThat(commandDeviceList).hasSize(databaseSizeBeforeUpdate);
        CommandDevice testCommandDevice = commandDeviceList.get(commandDeviceList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateCommandDeviceWithPatch() throws Exception {
        // Initialize the database
        commandDeviceRepository.saveAndFlush(commandDevice);

        int databaseSizeBeforeUpdate = commandDeviceRepository.findAll().size();

        // Update the commandDevice using partial update
        CommandDevice partialUpdatedCommandDevice = new CommandDevice();
        partialUpdatedCommandDevice.setId(commandDevice.getId());

        restCommandDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommandDevice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommandDevice))
            )
            .andExpect(status().isOk());

        // Validate the CommandDevice in the database
        List<CommandDevice> commandDeviceList = commandDeviceRepository.findAll();
        assertThat(commandDeviceList).hasSize(databaseSizeBeforeUpdate);
        CommandDevice testCommandDevice = commandDeviceList.get(commandDeviceList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingCommandDevice() throws Exception {
        int databaseSizeBeforeUpdate = commandDeviceRepository.findAll().size();
        commandDevice.setId(count.incrementAndGet());

        // Create the CommandDevice
        CommandDeviceDTO commandDeviceDTO = commandDeviceMapper.toDto(commandDevice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commandDeviceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commandDeviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommandDevice in the database
        List<CommandDevice> commandDeviceList = commandDeviceRepository.findAll();
        assertThat(commandDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommandDevice() throws Exception {
        int databaseSizeBeforeUpdate = commandDeviceRepository.findAll().size();
        commandDevice.setId(count.incrementAndGet());

        // Create the CommandDevice
        CommandDeviceDTO commandDeviceDTO = commandDeviceMapper.toDto(commandDevice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommandDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commandDeviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommandDevice in the database
        List<CommandDevice> commandDeviceList = commandDeviceRepository.findAll();
        assertThat(commandDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommandDevice() throws Exception {
        int databaseSizeBeforeUpdate = commandDeviceRepository.findAll().size();
        commandDevice.setId(count.incrementAndGet());

        // Create the CommandDevice
        CommandDeviceDTO commandDeviceDTO = commandDeviceMapper.toDto(commandDevice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommandDeviceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commandDeviceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommandDevice in the database
        List<CommandDevice> commandDeviceList = commandDeviceRepository.findAll();
        assertThat(commandDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommandDevice() throws Exception {
        // Initialize the database
        commandDeviceRepository.saveAndFlush(commandDevice);

        int databaseSizeBeforeDelete = commandDeviceRepository.findAll().size();

        // Delete the commandDevice
        restCommandDeviceMockMvc
            .perform(delete(ENTITY_API_URL_ID, commandDevice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommandDevice> commandDeviceList = commandDeviceRepository.findAll();
        assertThat(commandDeviceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
