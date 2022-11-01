package co.com.ies.smol.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.com.ies.smol.IntegrationTest;
import co.com.ies.smol.domain.CurrencyType;
import co.com.ies.smol.domain.Establishment;
import co.com.ies.smol.repository.CurrencyTypeRepository;
import co.com.ies.smol.service.criteria.CurrencyTypeCriteria;
import co.com.ies.smol.service.dto.CurrencyTypeDTO;
import co.com.ies.smol.service.mapper.CurrencyTypeMapper;
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
 * Integration tests for the {@link CurrencyTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CurrencyTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAA";
    private static final String UPDATED_CODE = "BBB";

    private static final String DEFAULT_SYMBOL = "AAAAA";
    private static final String UPDATED_SYMBOL = "BBBBB";

    private static final Boolean DEFAULT_IS_PRIORITY = false;
    private static final Boolean UPDATED_IS_PRIORITY = true;

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Float DEFAULT_EXCHANGE_RATE = 1F;
    private static final Float UPDATED_EXCHANGE_RATE = 2F;
    private static final Float SMALLER_EXCHANGE_RATE = 1F - 1F;

    private static final Integer DEFAULT_DECIMAL_PLACES = 1;
    private static final Integer UPDATED_DECIMAL_PLACES = 2;
    private static final Integer SMALLER_DECIMAL_PLACES = 1 - 1;

    private static final String DEFAULT_DECIMAL_SEPARATOR = "A";
    private static final String UPDATED_DECIMAL_SEPARATOR = "B";

    private static final String DEFAULT_THOUSAND_SEPARATOR = "A";
    private static final String UPDATED_THOUSAND_SEPARATOR = "B";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/currency-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CurrencyTypeRepository currencyTypeRepository;

    @Autowired
    private CurrencyTypeMapper currencyTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCurrencyTypeMockMvc;

    private CurrencyType currencyType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrencyType createEntity(EntityManager em) {
        CurrencyType currencyType = new CurrencyType()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .symbol(DEFAULT_SYMBOL)
            .isPriority(DEFAULT_IS_PRIORITY)
            .location(DEFAULT_LOCATION)
            .exchangeRate(DEFAULT_EXCHANGE_RATE)
            .decimalPlaces(DEFAULT_DECIMAL_PLACES)
            .decimalSeparator(DEFAULT_DECIMAL_SEPARATOR)
            .thousandSeparator(DEFAULT_THOUSAND_SEPARATOR)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Establishment establishment;
        if (TestUtil.findAll(em, Establishment.class).isEmpty()) {
            establishment = EstablishmentResourceIT.createEntity(em);
            em.persist(establishment);
            em.flush();
        } else {
            establishment = TestUtil.findAll(em, Establishment.class).get(0);
        }
        currencyType.setEstablishment(establishment);
        return currencyType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrencyType createUpdatedEntity(EntityManager em) {
        CurrencyType currencyType = new CurrencyType()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .symbol(UPDATED_SYMBOL)
            .isPriority(UPDATED_IS_PRIORITY)
            .location(UPDATED_LOCATION)
            .exchangeRate(UPDATED_EXCHANGE_RATE)
            .decimalPlaces(UPDATED_DECIMAL_PLACES)
            .decimalSeparator(UPDATED_DECIMAL_SEPARATOR)
            .thousandSeparator(UPDATED_THOUSAND_SEPARATOR)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        Establishment establishment;
        if (TestUtil.findAll(em, Establishment.class).isEmpty()) {
            establishment = EstablishmentResourceIT.createUpdatedEntity(em);
            em.persist(establishment);
            em.flush();
        } else {
            establishment = TestUtil.findAll(em, Establishment.class).get(0);
        }
        currencyType.setEstablishment(establishment);
        return currencyType;
    }

    @BeforeEach
    public void initTest() {
        currencyType = createEntity(em);
    }

    @Test
    @Transactional
    void createCurrencyType() throws Exception {
        int databaseSizeBeforeCreate = currencyTypeRepository.findAll().size();
        // Create the CurrencyType
        CurrencyTypeDTO currencyTypeDTO = currencyTypeMapper.toDto(currencyType);
        restCurrencyTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currencyTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CurrencyType in the database
        List<CurrencyType> currencyTypeList = currencyTypeRepository.findAll();
        assertThat(currencyTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CurrencyType testCurrencyType = currencyTypeList.get(currencyTypeList.size() - 1);
        assertThat(testCurrencyType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCurrencyType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCurrencyType.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testCurrencyType.getIsPriority()).isEqualTo(DEFAULT_IS_PRIORITY);
        assertThat(testCurrencyType.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testCurrencyType.getExchangeRate()).isEqualTo(DEFAULT_EXCHANGE_RATE);
        assertThat(testCurrencyType.getDecimalPlaces()).isEqualTo(DEFAULT_DECIMAL_PLACES);
        assertThat(testCurrencyType.getDecimalSeparator()).isEqualTo(DEFAULT_DECIMAL_SEPARATOR);
        assertThat(testCurrencyType.getThousandSeparator()).isEqualTo(DEFAULT_THOUSAND_SEPARATOR);
        assertThat(testCurrencyType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createCurrencyTypeWithExistingId() throws Exception {
        // Create the CurrencyType with an existing ID
        currencyType.setId(1L);
        CurrencyTypeDTO currencyTypeDTO = currencyTypeMapper.toDto(currencyType);

        int databaseSizeBeforeCreate = currencyTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrencyTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currencyTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrencyType in the database
        List<CurrencyType> currencyTypeList = currencyTypeRepository.findAll();
        assertThat(currencyTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyTypeRepository.findAll().size();
        // set the field null
        currencyType.setCode(null);

        // Create the CurrencyType, which fails.
        CurrencyTypeDTO currencyTypeDTO = currencyTypeMapper.toDto(currencyType);

        restCurrencyTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currencyTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<CurrencyType> currencyTypeList = currencyTypeRepository.findAll();
        assertThat(currencyTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSymbolIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyTypeRepository.findAll().size();
        // set the field null
        currencyType.setSymbol(null);

        // Create the CurrencyType, which fails.
        CurrencyTypeDTO currencyTypeDTO = currencyTypeMapper.toDto(currencyType);

        restCurrencyTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currencyTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<CurrencyType> currencyTypeList = currencyTypeRepository.findAll();
        assertThat(currencyTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCurrencyTypes() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList
        restCurrencyTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currencyType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL)))
            .andExpect(jsonPath("$.[*].isPriority").value(hasItem(DEFAULT_IS_PRIORITY.booleanValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].exchangeRate").value(hasItem(DEFAULT_EXCHANGE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].decimalPlaces").value(hasItem(DEFAULT_DECIMAL_PLACES)))
            .andExpect(jsonPath("$.[*].decimalSeparator").value(hasItem(DEFAULT_DECIMAL_SEPARATOR)))
            .andExpect(jsonPath("$.[*].thousandSeparator").value(hasItem(DEFAULT_THOUSAND_SEPARATOR)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getCurrencyType() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get the currencyType
        restCurrencyTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, currencyType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(currencyType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL))
            .andExpect(jsonPath("$.isPriority").value(DEFAULT_IS_PRIORITY.booleanValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.exchangeRate").value(DEFAULT_EXCHANGE_RATE.doubleValue()))
            .andExpect(jsonPath("$.decimalPlaces").value(DEFAULT_DECIMAL_PLACES))
            .andExpect(jsonPath("$.decimalSeparator").value(DEFAULT_DECIMAL_SEPARATOR))
            .andExpect(jsonPath("$.thousandSeparator").value(DEFAULT_THOUSAND_SEPARATOR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getCurrencyTypesByIdFiltering() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        Long id = currencyType.getId();

        defaultCurrencyTypeShouldBeFound("id.equals=" + id);
        defaultCurrencyTypeShouldNotBeFound("id.notEquals=" + id);

        defaultCurrencyTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCurrencyTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultCurrencyTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCurrencyTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where name equals to DEFAULT_NAME
        defaultCurrencyTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the currencyTypeList where name equals to UPDATED_NAME
        defaultCurrencyTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCurrencyTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the currencyTypeList where name equals to UPDATED_NAME
        defaultCurrencyTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where name is not null
        defaultCurrencyTypeShouldBeFound("name.specified=true");

        // Get all the currencyTypeList where name is null
        defaultCurrencyTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where name contains DEFAULT_NAME
        defaultCurrencyTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the currencyTypeList where name contains UPDATED_NAME
        defaultCurrencyTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where name does not contain DEFAULT_NAME
        defaultCurrencyTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the currencyTypeList where name does not contain UPDATED_NAME
        defaultCurrencyTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where code equals to DEFAULT_CODE
        defaultCurrencyTypeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the currencyTypeList where code equals to UPDATED_CODE
        defaultCurrencyTypeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCurrencyTypeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the currencyTypeList where code equals to UPDATED_CODE
        defaultCurrencyTypeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where code is not null
        defaultCurrencyTypeShouldBeFound("code.specified=true");

        // Get all the currencyTypeList where code is null
        defaultCurrencyTypeShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByCodeContainsSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where code contains DEFAULT_CODE
        defaultCurrencyTypeShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the currencyTypeList where code contains UPDATED_CODE
        defaultCurrencyTypeShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where code does not contain DEFAULT_CODE
        defaultCurrencyTypeShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the currencyTypeList where code does not contain UPDATED_CODE
        defaultCurrencyTypeShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesBySymbolIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where symbol equals to DEFAULT_SYMBOL
        defaultCurrencyTypeShouldBeFound("symbol.equals=" + DEFAULT_SYMBOL);

        // Get all the currencyTypeList where symbol equals to UPDATED_SYMBOL
        defaultCurrencyTypeShouldNotBeFound("symbol.equals=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesBySymbolIsInShouldWork() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where symbol in DEFAULT_SYMBOL or UPDATED_SYMBOL
        defaultCurrencyTypeShouldBeFound("symbol.in=" + DEFAULT_SYMBOL + "," + UPDATED_SYMBOL);

        // Get all the currencyTypeList where symbol equals to UPDATED_SYMBOL
        defaultCurrencyTypeShouldNotBeFound("symbol.in=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesBySymbolIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where symbol is not null
        defaultCurrencyTypeShouldBeFound("symbol.specified=true");

        // Get all the currencyTypeList where symbol is null
        defaultCurrencyTypeShouldNotBeFound("symbol.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrencyTypesBySymbolContainsSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where symbol contains DEFAULT_SYMBOL
        defaultCurrencyTypeShouldBeFound("symbol.contains=" + DEFAULT_SYMBOL);

        // Get all the currencyTypeList where symbol contains UPDATED_SYMBOL
        defaultCurrencyTypeShouldNotBeFound("symbol.contains=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesBySymbolNotContainsSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where symbol does not contain DEFAULT_SYMBOL
        defaultCurrencyTypeShouldNotBeFound("symbol.doesNotContain=" + DEFAULT_SYMBOL);

        // Get all the currencyTypeList where symbol does not contain UPDATED_SYMBOL
        defaultCurrencyTypeShouldBeFound("symbol.doesNotContain=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByIsPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where isPriority equals to DEFAULT_IS_PRIORITY
        defaultCurrencyTypeShouldBeFound("isPriority.equals=" + DEFAULT_IS_PRIORITY);

        // Get all the currencyTypeList where isPriority equals to UPDATED_IS_PRIORITY
        defaultCurrencyTypeShouldNotBeFound("isPriority.equals=" + UPDATED_IS_PRIORITY);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByIsPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where isPriority in DEFAULT_IS_PRIORITY or UPDATED_IS_PRIORITY
        defaultCurrencyTypeShouldBeFound("isPriority.in=" + DEFAULT_IS_PRIORITY + "," + UPDATED_IS_PRIORITY);

        // Get all the currencyTypeList where isPriority equals to UPDATED_IS_PRIORITY
        defaultCurrencyTypeShouldNotBeFound("isPriority.in=" + UPDATED_IS_PRIORITY);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByIsPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where isPriority is not null
        defaultCurrencyTypeShouldBeFound("isPriority.specified=true");

        // Get all the currencyTypeList where isPriority is null
        defaultCurrencyTypeShouldNotBeFound("isPriority.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where location equals to DEFAULT_LOCATION
        defaultCurrencyTypeShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the currencyTypeList where location equals to UPDATED_LOCATION
        defaultCurrencyTypeShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultCurrencyTypeShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the currencyTypeList where location equals to UPDATED_LOCATION
        defaultCurrencyTypeShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where location is not null
        defaultCurrencyTypeShouldBeFound("location.specified=true");

        // Get all the currencyTypeList where location is null
        defaultCurrencyTypeShouldNotBeFound("location.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByLocationContainsSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where location contains DEFAULT_LOCATION
        defaultCurrencyTypeShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the currencyTypeList where location contains UPDATED_LOCATION
        defaultCurrencyTypeShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where location does not contain DEFAULT_LOCATION
        defaultCurrencyTypeShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the currencyTypeList where location does not contain UPDATED_LOCATION
        defaultCurrencyTypeShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByExchangeRateIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where exchangeRate equals to DEFAULT_EXCHANGE_RATE
        defaultCurrencyTypeShouldBeFound("exchangeRate.equals=" + DEFAULT_EXCHANGE_RATE);

        // Get all the currencyTypeList where exchangeRate equals to UPDATED_EXCHANGE_RATE
        defaultCurrencyTypeShouldNotBeFound("exchangeRate.equals=" + UPDATED_EXCHANGE_RATE);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByExchangeRateIsInShouldWork() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where exchangeRate in DEFAULT_EXCHANGE_RATE or UPDATED_EXCHANGE_RATE
        defaultCurrencyTypeShouldBeFound("exchangeRate.in=" + DEFAULT_EXCHANGE_RATE + "," + UPDATED_EXCHANGE_RATE);

        // Get all the currencyTypeList where exchangeRate equals to UPDATED_EXCHANGE_RATE
        defaultCurrencyTypeShouldNotBeFound("exchangeRate.in=" + UPDATED_EXCHANGE_RATE);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByExchangeRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where exchangeRate is not null
        defaultCurrencyTypeShouldBeFound("exchangeRate.specified=true");

        // Get all the currencyTypeList where exchangeRate is null
        defaultCurrencyTypeShouldNotBeFound("exchangeRate.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByExchangeRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where exchangeRate is greater than or equal to DEFAULT_EXCHANGE_RATE
        defaultCurrencyTypeShouldBeFound("exchangeRate.greaterThanOrEqual=" + DEFAULT_EXCHANGE_RATE);

        // Get all the currencyTypeList where exchangeRate is greater than or equal to UPDATED_EXCHANGE_RATE
        defaultCurrencyTypeShouldNotBeFound("exchangeRate.greaterThanOrEqual=" + UPDATED_EXCHANGE_RATE);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByExchangeRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where exchangeRate is less than or equal to DEFAULT_EXCHANGE_RATE
        defaultCurrencyTypeShouldBeFound("exchangeRate.lessThanOrEqual=" + DEFAULT_EXCHANGE_RATE);

        // Get all the currencyTypeList where exchangeRate is less than or equal to SMALLER_EXCHANGE_RATE
        defaultCurrencyTypeShouldNotBeFound("exchangeRate.lessThanOrEqual=" + SMALLER_EXCHANGE_RATE);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByExchangeRateIsLessThanSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where exchangeRate is less than DEFAULT_EXCHANGE_RATE
        defaultCurrencyTypeShouldNotBeFound("exchangeRate.lessThan=" + DEFAULT_EXCHANGE_RATE);

        // Get all the currencyTypeList where exchangeRate is less than UPDATED_EXCHANGE_RATE
        defaultCurrencyTypeShouldBeFound("exchangeRate.lessThan=" + UPDATED_EXCHANGE_RATE);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByExchangeRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where exchangeRate is greater than DEFAULT_EXCHANGE_RATE
        defaultCurrencyTypeShouldNotBeFound("exchangeRate.greaterThan=" + DEFAULT_EXCHANGE_RATE);

        // Get all the currencyTypeList where exchangeRate is greater than SMALLER_EXCHANGE_RATE
        defaultCurrencyTypeShouldBeFound("exchangeRate.greaterThan=" + SMALLER_EXCHANGE_RATE);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByDecimalPlacesIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where decimalPlaces equals to DEFAULT_DECIMAL_PLACES
        defaultCurrencyTypeShouldBeFound("decimalPlaces.equals=" + DEFAULT_DECIMAL_PLACES);

        // Get all the currencyTypeList where decimalPlaces equals to UPDATED_DECIMAL_PLACES
        defaultCurrencyTypeShouldNotBeFound("decimalPlaces.equals=" + UPDATED_DECIMAL_PLACES);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByDecimalPlacesIsInShouldWork() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where decimalPlaces in DEFAULT_DECIMAL_PLACES or UPDATED_DECIMAL_PLACES
        defaultCurrencyTypeShouldBeFound("decimalPlaces.in=" + DEFAULT_DECIMAL_PLACES + "," + UPDATED_DECIMAL_PLACES);

        // Get all the currencyTypeList where decimalPlaces equals to UPDATED_DECIMAL_PLACES
        defaultCurrencyTypeShouldNotBeFound("decimalPlaces.in=" + UPDATED_DECIMAL_PLACES);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByDecimalPlacesIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where decimalPlaces is not null
        defaultCurrencyTypeShouldBeFound("decimalPlaces.specified=true");

        // Get all the currencyTypeList where decimalPlaces is null
        defaultCurrencyTypeShouldNotBeFound("decimalPlaces.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByDecimalPlacesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where decimalPlaces is greater than or equal to DEFAULT_DECIMAL_PLACES
        defaultCurrencyTypeShouldBeFound("decimalPlaces.greaterThanOrEqual=" + DEFAULT_DECIMAL_PLACES);

        // Get all the currencyTypeList where decimalPlaces is greater than or equal to UPDATED_DECIMAL_PLACES
        defaultCurrencyTypeShouldNotBeFound("decimalPlaces.greaterThanOrEqual=" + UPDATED_DECIMAL_PLACES);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByDecimalPlacesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where decimalPlaces is less than or equal to DEFAULT_DECIMAL_PLACES
        defaultCurrencyTypeShouldBeFound("decimalPlaces.lessThanOrEqual=" + DEFAULT_DECIMAL_PLACES);

        // Get all the currencyTypeList where decimalPlaces is less than or equal to SMALLER_DECIMAL_PLACES
        defaultCurrencyTypeShouldNotBeFound("decimalPlaces.lessThanOrEqual=" + SMALLER_DECIMAL_PLACES);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByDecimalPlacesIsLessThanSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where decimalPlaces is less than DEFAULT_DECIMAL_PLACES
        defaultCurrencyTypeShouldNotBeFound("decimalPlaces.lessThan=" + DEFAULT_DECIMAL_PLACES);

        // Get all the currencyTypeList where decimalPlaces is less than UPDATED_DECIMAL_PLACES
        defaultCurrencyTypeShouldBeFound("decimalPlaces.lessThan=" + UPDATED_DECIMAL_PLACES);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByDecimalPlacesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where decimalPlaces is greater than DEFAULT_DECIMAL_PLACES
        defaultCurrencyTypeShouldNotBeFound("decimalPlaces.greaterThan=" + DEFAULT_DECIMAL_PLACES);

        // Get all the currencyTypeList where decimalPlaces is greater than SMALLER_DECIMAL_PLACES
        defaultCurrencyTypeShouldBeFound("decimalPlaces.greaterThan=" + SMALLER_DECIMAL_PLACES);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByDecimalSeparatorIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where decimalSeparator equals to DEFAULT_DECIMAL_SEPARATOR
        defaultCurrencyTypeShouldBeFound("decimalSeparator.equals=" + DEFAULT_DECIMAL_SEPARATOR);

        // Get all the currencyTypeList where decimalSeparator equals to UPDATED_DECIMAL_SEPARATOR
        defaultCurrencyTypeShouldNotBeFound("decimalSeparator.equals=" + UPDATED_DECIMAL_SEPARATOR);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByDecimalSeparatorIsInShouldWork() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where decimalSeparator in DEFAULT_DECIMAL_SEPARATOR or UPDATED_DECIMAL_SEPARATOR
        defaultCurrencyTypeShouldBeFound("decimalSeparator.in=" + DEFAULT_DECIMAL_SEPARATOR + "," + UPDATED_DECIMAL_SEPARATOR);

        // Get all the currencyTypeList where decimalSeparator equals to UPDATED_DECIMAL_SEPARATOR
        defaultCurrencyTypeShouldNotBeFound("decimalSeparator.in=" + UPDATED_DECIMAL_SEPARATOR);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByDecimalSeparatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where decimalSeparator is not null
        defaultCurrencyTypeShouldBeFound("decimalSeparator.specified=true");

        // Get all the currencyTypeList where decimalSeparator is null
        defaultCurrencyTypeShouldNotBeFound("decimalSeparator.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByDecimalSeparatorContainsSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where decimalSeparator contains DEFAULT_DECIMAL_SEPARATOR
        defaultCurrencyTypeShouldBeFound("decimalSeparator.contains=" + DEFAULT_DECIMAL_SEPARATOR);

        // Get all the currencyTypeList where decimalSeparator contains UPDATED_DECIMAL_SEPARATOR
        defaultCurrencyTypeShouldNotBeFound("decimalSeparator.contains=" + UPDATED_DECIMAL_SEPARATOR);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByDecimalSeparatorNotContainsSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where decimalSeparator does not contain DEFAULT_DECIMAL_SEPARATOR
        defaultCurrencyTypeShouldNotBeFound("decimalSeparator.doesNotContain=" + DEFAULT_DECIMAL_SEPARATOR);

        // Get all the currencyTypeList where decimalSeparator does not contain UPDATED_DECIMAL_SEPARATOR
        defaultCurrencyTypeShouldBeFound("decimalSeparator.doesNotContain=" + UPDATED_DECIMAL_SEPARATOR);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByThousandSeparatorIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where thousandSeparator equals to DEFAULT_THOUSAND_SEPARATOR
        defaultCurrencyTypeShouldBeFound("thousandSeparator.equals=" + DEFAULT_THOUSAND_SEPARATOR);

        // Get all the currencyTypeList where thousandSeparator equals to UPDATED_THOUSAND_SEPARATOR
        defaultCurrencyTypeShouldNotBeFound("thousandSeparator.equals=" + UPDATED_THOUSAND_SEPARATOR);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByThousandSeparatorIsInShouldWork() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where thousandSeparator in DEFAULT_THOUSAND_SEPARATOR or UPDATED_THOUSAND_SEPARATOR
        defaultCurrencyTypeShouldBeFound("thousandSeparator.in=" + DEFAULT_THOUSAND_SEPARATOR + "," + UPDATED_THOUSAND_SEPARATOR);

        // Get all the currencyTypeList where thousandSeparator equals to UPDATED_THOUSAND_SEPARATOR
        defaultCurrencyTypeShouldNotBeFound("thousandSeparator.in=" + UPDATED_THOUSAND_SEPARATOR);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByThousandSeparatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where thousandSeparator is not null
        defaultCurrencyTypeShouldBeFound("thousandSeparator.specified=true");

        // Get all the currencyTypeList where thousandSeparator is null
        defaultCurrencyTypeShouldNotBeFound("thousandSeparator.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByThousandSeparatorContainsSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where thousandSeparator contains DEFAULT_THOUSAND_SEPARATOR
        defaultCurrencyTypeShouldBeFound("thousandSeparator.contains=" + DEFAULT_THOUSAND_SEPARATOR);

        // Get all the currencyTypeList where thousandSeparator contains UPDATED_THOUSAND_SEPARATOR
        defaultCurrencyTypeShouldNotBeFound("thousandSeparator.contains=" + UPDATED_THOUSAND_SEPARATOR);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByThousandSeparatorNotContainsSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where thousandSeparator does not contain DEFAULT_THOUSAND_SEPARATOR
        defaultCurrencyTypeShouldNotBeFound("thousandSeparator.doesNotContain=" + DEFAULT_THOUSAND_SEPARATOR);

        // Get all the currencyTypeList where thousandSeparator does not contain UPDATED_THOUSAND_SEPARATOR
        defaultCurrencyTypeShouldBeFound("thousandSeparator.doesNotContain=" + UPDATED_THOUSAND_SEPARATOR);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where description equals to DEFAULT_DESCRIPTION
        defaultCurrencyTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the currencyTypeList where description equals to UPDATED_DESCRIPTION
        defaultCurrencyTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCurrencyTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the currencyTypeList where description equals to UPDATED_DESCRIPTION
        defaultCurrencyTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where description is not null
        defaultCurrencyTypeShouldBeFound("description.specified=true");

        // Get all the currencyTypeList where description is null
        defaultCurrencyTypeShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where description contains DEFAULT_DESCRIPTION
        defaultCurrencyTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the currencyTypeList where description contains UPDATED_DESCRIPTION
        defaultCurrencyTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        // Get all the currencyTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultCurrencyTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the currencyTypeList where description does not contain UPDATED_DESCRIPTION
        defaultCurrencyTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCurrencyTypesByEstablishmentIsEqualToSomething() throws Exception {
        Establishment establishment;
        if (TestUtil.findAll(em, Establishment.class).isEmpty()) {
            currencyTypeRepository.saveAndFlush(currencyType);
            establishment = EstablishmentResourceIT.createEntity(em);
        } else {
            establishment = TestUtil.findAll(em, Establishment.class).get(0);
        }
        em.persist(establishment);
        em.flush();
        currencyType.setEstablishment(establishment);
        currencyTypeRepository.saveAndFlush(currencyType);
        Long establishmentId = establishment.getId();

        // Get all the currencyTypeList where establishment equals to establishmentId
        defaultCurrencyTypeShouldBeFound("establishmentId.equals=" + establishmentId);

        // Get all the currencyTypeList where establishment equals to (establishmentId + 1)
        defaultCurrencyTypeShouldNotBeFound("establishmentId.equals=" + (establishmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCurrencyTypeShouldBeFound(String filter) throws Exception {
        restCurrencyTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currencyType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL)))
            .andExpect(jsonPath("$.[*].isPriority").value(hasItem(DEFAULT_IS_PRIORITY.booleanValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].exchangeRate").value(hasItem(DEFAULT_EXCHANGE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].decimalPlaces").value(hasItem(DEFAULT_DECIMAL_PLACES)))
            .andExpect(jsonPath("$.[*].decimalSeparator").value(hasItem(DEFAULT_DECIMAL_SEPARATOR)))
            .andExpect(jsonPath("$.[*].thousandSeparator").value(hasItem(DEFAULT_THOUSAND_SEPARATOR)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restCurrencyTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCurrencyTypeShouldNotBeFound(String filter) throws Exception {
        restCurrencyTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCurrencyTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCurrencyType() throws Exception {
        // Get the currencyType
        restCurrencyTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCurrencyType() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        int databaseSizeBeforeUpdate = currencyTypeRepository.findAll().size();

        // Update the currencyType
        CurrencyType updatedCurrencyType = currencyTypeRepository.findById(currencyType.getId()).get();
        // Disconnect from session so that the updates on updatedCurrencyType are not directly saved in db
        em.detach(updatedCurrencyType);
        updatedCurrencyType
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .symbol(UPDATED_SYMBOL)
            .isPriority(UPDATED_IS_PRIORITY)
            .location(UPDATED_LOCATION)
            .exchangeRate(UPDATED_EXCHANGE_RATE)
            .decimalPlaces(UPDATED_DECIMAL_PLACES)
            .decimalSeparator(UPDATED_DECIMAL_SEPARATOR)
            .thousandSeparator(UPDATED_THOUSAND_SEPARATOR)
            .description(UPDATED_DESCRIPTION);
        CurrencyTypeDTO currencyTypeDTO = currencyTypeMapper.toDto(updatedCurrencyType);

        restCurrencyTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, currencyTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currencyTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CurrencyType in the database
        List<CurrencyType> currencyTypeList = currencyTypeRepository.findAll();
        assertThat(currencyTypeList).hasSize(databaseSizeBeforeUpdate);
        CurrencyType testCurrencyType = currencyTypeList.get(currencyTypeList.size() - 1);
        assertThat(testCurrencyType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCurrencyType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCurrencyType.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testCurrencyType.getIsPriority()).isEqualTo(UPDATED_IS_PRIORITY);
        assertThat(testCurrencyType.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCurrencyType.getExchangeRate()).isEqualTo(UPDATED_EXCHANGE_RATE);
        assertThat(testCurrencyType.getDecimalPlaces()).isEqualTo(UPDATED_DECIMAL_PLACES);
        assertThat(testCurrencyType.getDecimalSeparator()).isEqualTo(UPDATED_DECIMAL_SEPARATOR);
        assertThat(testCurrencyType.getThousandSeparator()).isEqualTo(UPDATED_THOUSAND_SEPARATOR);
        assertThat(testCurrencyType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingCurrencyType() throws Exception {
        int databaseSizeBeforeUpdate = currencyTypeRepository.findAll().size();
        currencyType.setId(count.incrementAndGet());

        // Create the CurrencyType
        CurrencyTypeDTO currencyTypeDTO = currencyTypeMapper.toDto(currencyType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, currencyTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currencyTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrencyType in the database
        List<CurrencyType> currencyTypeList = currencyTypeRepository.findAll();
        assertThat(currencyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCurrencyType() throws Exception {
        int databaseSizeBeforeUpdate = currencyTypeRepository.findAll().size();
        currencyType.setId(count.incrementAndGet());

        // Create the CurrencyType
        CurrencyTypeDTO currencyTypeDTO = currencyTypeMapper.toDto(currencyType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrencyTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(currencyTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrencyType in the database
        List<CurrencyType> currencyTypeList = currencyTypeRepository.findAll();
        assertThat(currencyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCurrencyType() throws Exception {
        int databaseSizeBeforeUpdate = currencyTypeRepository.findAll().size();
        currencyType.setId(count.incrementAndGet());

        // Create the CurrencyType
        CurrencyTypeDTO currencyTypeDTO = currencyTypeMapper.toDto(currencyType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrencyTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(currencyTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CurrencyType in the database
        List<CurrencyType> currencyTypeList = currencyTypeRepository.findAll();
        assertThat(currencyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCurrencyTypeWithPatch() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        int databaseSizeBeforeUpdate = currencyTypeRepository.findAll().size();

        // Update the currencyType using partial update
        CurrencyType partialUpdatedCurrencyType = new CurrencyType();
        partialUpdatedCurrencyType.setId(currencyType.getId());

        partialUpdatedCurrencyType
            .isPriority(UPDATED_IS_PRIORITY)
            .location(UPDATED_LOCATION)
            .decimalSeparator(UPDATED_DECIMAL_SEPARATOR)
            .thousandSeparator(UPDATED_THOUSAND_SEPARATOR)
            .description(UPDATED_DESCRIPTION);

        restCurrencyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrencyType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurrencyType))
            )
            .andExpect(status().isOk());

        // Validate the CurrencyType in the database
        List<CurrencyType> currencyTypeList = currencyTypeRepository.findAll();
        assertThat(currencyTypeList).hasSize(databaseSizeBeforeUpdate);
        CurrencyType testCurrencyType = currencyTypeList.get(currencyTypeList.size() - 1);
        assertThat(testCurrencyType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCurrencyType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCurrencyType.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testCurrencyType.getIsPriority()).isEqualTo(UPDATED_IS_PRIORITY);
        assertThat(testCurrencyType.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCurrencyType.getExchangeRate()).isEqualTo(DEFAULT_EXCHANGE_RATE);
        assertThat(testCurrencyType.getDecimalPlaces()).isEqualTo(DEFAULT_DECIMAL_PLACES);
        assertThat(testCurrencyType.getDecimalSeparator()).isEqualTo(UPDATED_DECIMAL_SEPARATOR);
        assertThat(testCurrencyType.getThousandSeparator()).isEqualTo(UPDATED_THOUSAND_SEPARATOR);
        assertThat(testCurrencyType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateCurrencyTypeWithPatch() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        int databaseSizeBeforeUpdate = currencyTypeRepository.findAll().size();

        // Update the currencyType using partial update
        CurrencyType partialUpdatedCurrencyType = new CurrencyType();
        partialUpdatedCurrencyType.setId(currencyType.getId());

        partialUpdatedCurrencyType
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .symbol(UPDATED_SYMBOL)
            .isPriority(UPDATED_IS_PRIORITY)
            .location(UPDATED_LOCATION)
            .exchangeRate(UPDATED_EXCHANGE_RATE)
            .decimalPlaces(UPDATED_DECIMAL_PLACES)
            .decimalSeparator(UPDATED_DECIMAL_SEPARATOR)
            .thousandSeparator(UPDATED_THOUSAND_SEPARATOR)
            .description(UPDATED_DESCRIPTION);

        restCurrencyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCurrencyType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCurrencyType))
            )
            .andExpect(status().isOk());

        // Validate the CurrencyType in the database
        List<CurrencyType> currencyTypeList = currencyTypeRepository.findAll();
        assertThat(currencyTypeList).hasSize(databaseSizeBeforeUpdate);
        CurrencyType testCurrencyType = currencyTypeList.get(currencyTypeList.size() - 1);
        assertThat(testCurrencyType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCurrencyType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCurrencyType.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testCurrencyType.getIsPriority()).isEqualTo(UPDATED_IS_PRIORITY);
        assertThat(testCurrencyType.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCurrencyType.getExchangeRate()).isEqualTo(UPDATED_EXCHANGE_RATE);
        assertThat(testCurrencyType.getDecimalPlaces()).isEqualTo(UPDATED_DECIMAL_PLACES);
        assertThat(testCurrencyType.getDecimalSeparator()).isEqualTo(UPDATED_DECIMAL_SEPARATOR);
        assertThat(testCurrencyType.getThousandSeparator()).isEqualTo(UPDATED_THOUSAND_SEPARATOR);
        assertThat(testCurrencyType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingCurrencyType() throws Exception {
        int databaseSizeBeforeUpdate = currencyTypeRepository.findAll().size();
        currencyType.setId(count.incrementAndGet());

        // Create the CurrencyType
        CurrencyTypeDTO currencyTypeDTO = currencyTypeMapper.toDto(currencyType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, currencyTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currencyTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrencyType in the database
        List<CurrencyType> currencyTypeList = currencyTypeRepository.findAll();
        assertThat(currencyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCurrencyType() throws Exception {
        int databaseSizeBeforeUpdate = currencyTypeRepository.findAll().size();
        currencyType.setId(count.incrementAndGet());

        // Create the CurrencyType
        CurrencyTypeDTO currencyTypeDTO = currencyTypeMapper.toDto(currencyType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrencyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currencyTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CurrencyType in the database
        List<CurrencyType> currencyTypeList = currencyTypeRepository.findAll();
        assertThat(currencyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCurrencyType() throws Exception {
        int databaseSizeBeforeUpdate = currencyTypeRepository.findAll().size();
        currencyType.setId(count.incrementAndGet());

        // Create the CurrencyType
        CurrencyTypeDTO currencyTypeDTO = currencyTypeMapper.toDto(currencyType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCurrencyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(currencyTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CurrencyType in the database
        List<CurrencyType> currencyTypeList = currencyTypeRepository.findAll();
        assertThat(currencyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCurrencyType() throws Exception {
        // Initialize the database
        currencyTypeRepository.saveAndFlush(currencyType);

        int databaseSizeBeforeDelete = currencyTypeRepository.findAll().size();

        // Delete the currencyType
        restCurrencyTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, currencyType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CurrencyType> currencyTypeList = currencyTypeRepository.findAll();
        assertThat(currencyTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
