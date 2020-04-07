package com.isoft.trc.web.rest;

import com.isoft.trc.PandemicTrackingApp;
import com.isoft.trc.domain.PersonTracking;
import com.isoft.trc.repository.PersonTrackingRepository;
import com.isoft.trc.service.PersonTrackingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PersonTrackingResource} REST controller.
 */
@SpringBootTest(classes = PandemicTrackingApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PersonTrackingResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Double DEFAULT_LOCATION_LONGITUDE = 1D;
    private static final Double UPDATED_LOCATION_LONGITUDE = 2D;

    private static final Double DEFAULT_LOCATION_LATITUDE = 1D;
    private static final Double UPDATED_LOCATION_LATITUDE = 2D;

    private static final Instant DEFAULT_LOCATION_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOCATION_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PersonTrackingRepository personTrackingRepository;

    @Autowired
    private PersonTrackingService personTrackingService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonTrackingMockMvc;

    private PersonTracking personTracking;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonTracking createEntity(EntityManager em) {
        PersonTracking personTracking = new PersonTracking()
            .userId(DEFAULT_USER_ID)
            .locationLongitude(DEFAULT_LOCATION_LONGITUDE)
            .locationLatitude(DEFAULT_LOCATION_LATITUDE)
            .locationTime(DEFAULT_LOCATION_TIME);
        return personTracking;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonTracking createUpdatedEntity(EntityManager em) {
        PersonTracking personTracking = new PersonTracking()
            .userId(UPDATED_USER_ID)
            .locationLongitude(UPDATED_LOCATION_LONGITUDE)
            .locationLatitude(UPDATED_LOCATION_LATITUDE)
            .locationTime(UPDATED_LOCATION_TIME);
        return personTracking;
    }

    @BeforeEach
    public void initTest() {
        personTracking = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonTracking() throws Exception {
        int databaseSizeBeforeCreate = personTrackingRepository.findAll().size();

        // Create the PersonTracking
        restPersonTrackingMockMvc.perform(post("/api/person-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personTracking)))
            .andExpect(status().isCreated());

        // Validate the PersonTracking in the database
        List<PersonTracking> personTrackingList = personTrackingRepository.findAll();
        assertThat(personTrackingList).hasSize(databaseSizeBeforeCreate + 1);
        PersonTracking testPersonTracking = personTrackingList.get(personTrackingList.size() - 1);
        assertThat(testPersonTracking.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPersonTracking.getLocationLongitude()).isEqualTo(DEFAULT_LOCATION_LONGITUDE);
        assertThat(testPersonTracking.getLocationLatitude()).isEqualTo(DEFAULT_LOCATION_LATITUDE);
        assertThat(testPersonTracking.getLocationTime()).isEqualTo(DEFAULT_LOCATION_TIME);
    }

    @Test
    @Transactional
    public void createPersonTrackingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personTrackingRepository.findAll().size();

        // Create the PersonTracking with an existing ID
        personTracking.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonTrackingMockMvc.perform(post("/api/person-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personTracking)))
            .andExpect(status().isBadRequest());

        // Validate the PersonTracking in the database
        List<PersonTracking> personTrackingList = personTrackingRepository.findAll();
        assertThat(personTrackingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = personTrackingRepository.findAll().size();
        // set the field null
        personTracking.setUserId(null);

        // Create the PersonTracking, which fails.

        restPersonTrackingMockMvc.perform(post("/api/person-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personTracking)))
            .andExpect(status().isBadRequest());

        List<PersonTracking> personTrackingList = personTrackingRepository.findAll();
        assertThat(personTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personTrackingRepository.findAll().size();
        // set the field null
        personTracking.setLocationLongitude(null);

        // Create the PersonTracking, which fails.

        restPersonTrackingMockMvc.perform(post("/api/person-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personTracking)))
            .andExpect(status().isBadRequest());

        List<PersonTracking> personTrackingList = personTrackingRepository.findAll();
        assertThat(personTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personTrackingRepository.findAll().size();
        // set the field null
        personTracking.setLocationLatitude(null);

        // Create the PersonTracking, which fails.

        restPersonTrackingMockMvc.perform(post("/api/person-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personTracking)))
            .andExpect(status().isBadRequest());

        List<PersonTracking> personTrackingList = personTrackingRepository.findAll();
        assertThat(personTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonTrackings() throws Exception {
        // Initialize the database
        personTrackingRepository.saveAndFlush(personTracking);

        // Get all the personTrackingList
        restPersonTrackingMockMvc.perform(get("/api/person-trackings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personTracking.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].locationLongitude").value(hasItem(DEFAULT_LOCATION_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].locationLatitude").value(hasItem(DEFAULT_LOCATION_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].locationTime").value(hasItem(DEFAULT_LOCATION_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getPersonTracking() throws Exception {
        // Initialize the database
        personTrackingRepository.saveAndFlush(personTracking);

        // Get the personTracking
        restPersonTrackingMockMvc.perform(get("/api/person-trackings/{id}", personTracking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personTracking.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.locationLongitude").value(DEFAULT_LOCATION_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.locationLatitude").value(DEFAULT_LOCATION_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.locationTime").value(DEFAULT_LOCATION_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonTracking() throws Exception {
        // Get the personTracking
        restPersonTrackingMockMvc.perform(get("/api/person-trackings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonTracking() throws Exception {
        // Initialize the database
        personTrackingService.save(personTracking);

        int databaseSizeBeforeUpdate = personTrackingRepository.findAll().size();

        // Update the personTracking
        PersonTracking updatedPersonTracking = personTrackingRepository.findById(personTracking.getId()).get();
        // Disconnect from session so that the updates on updatedPersonTracking are not directly saved in db
        em.detach(updatedPersonTracking);
        updatedPersonTracking
            .userId(UPDATED_USER_ID)
            .locationLongitude(UPDATED_LOCATION_LONGITUDE)
            .locationLatitude(UPDATED_LOCATION_LATITUDE)
            .locationTime(UPDATED_LOCATION_TIME);

        restPersonTrackingMockMvc.perform(put("/api/person-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonTracking)))
            .andExpect(status().isOk());

        // Validate the PersonTracking in the database
        List<PersonTracking> personTrackingList = personTrackingRepository.findAll();
        assertThat(personTrackingList).hasSize(databaseSizeBeforeUpdate);
        PersonTracking testPersonTracking = personTrackingList.get(personTrackingList.size() - 1);
        assertThat(testPersonTracking.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPersonTracking.getLocationLongitude()).isEqualTo(UPDATED_LOCATION_LONGITUDE);
        assertThat(testPersonTracking.getLocationLatitude()).isEqualTo(UPDATED_LOCATION_LATITUDE);
        assertThat(testPersonTracking.getLocationTime()).isEqualTo(UPDATED_LOCATION_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonTracking() throws Exception {
        int databaseSizeBeforeUpdate = personTrackingRepository.findAll().size();

        // Create the PersonTracking

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonTrackingMockMvc.perform(put("/api/person-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personTracking)))
            .andExpect(status().isBadRequest());

        // Validate the PersonTracking in the database
        List<PersonTracking> personTrackingList = personTrackingRepository.findAll();
        assertThat(personTrackingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonTracking() throws Exception {
        // Initialize the database
        personTrackingService.save(personTracking);

        int databaseSizeBeforeDelete = personTrackingRepository.findAll().size();

        // Delete the personTracking
        restPersonTrackingMockMvc.perform(delete("/api/person-trackings/{id}", personTracking.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonTracking> personTrackingList = personTrackingRepository.findAll();
        assertThat(personTrackingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
