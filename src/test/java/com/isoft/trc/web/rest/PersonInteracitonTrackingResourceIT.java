package com.isoft.trc.web.rest;

import com.isoft.trc.PandemicTrackingApp;
import com.isoft.trc.domain.PersonInteracitonTracking;
import com.isoft.trc.repository.PersonInteracitonTrackingRepository;
import com.isoft.trc.service.PersonInteracitonTrackingService;

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
 * Integration tests for the {@link PersonInteracitonTrackingResource} REST controller.
 */
@SpringBootTest(classes = PandemicTrackingApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PersonInteracitonTrackingResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_INTERACTED_USER_ID = 1L;
    private static final Long UPDATED_INTERACTED_USER_ID = 2L;

    private static final Double DEFAULT_LOCATION_LONGITUDE = 1D;
    private static final Double UPDATED_LOCATION_LONGITUDE = 2D;

    private static final Double DEFAULT_LOCATION_LATITUDE = 1D;
    private static final Double UPDATED_LOCATION_LATITUDE = 2D;

    private static final Instant DEFAULT_LOCATION_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOCATION_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PersonInteracitonTrackingRepository personInteracitonTrackingRepository;

    @Autowired
    private PersonInteracitonTrackingService personInteracitonTrackingService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonInteracitonTrackingMockMvc;

    private PersonInteracitonTracking personInteracitonTracking;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonInteracitonTracking createEntity(EntityManager em) {
        PersonInteracitonTracking personInteracitonTracking = new PersonInteracitonTracking()
            .userId(DEFAULT_USER_ID)
            .interactedUserId(DEFAULT_INTERACTED_USER_ID)
            .locationLongitude(DEFAULT_LOCATION_LONGITUDE)
            .locationLatitude(DEFAULT_LOCATION_LATITUDE)
            .locationTime(DEFAULT_LOCATION_TIME);
        return personInteracitonTracking;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonInteracitonTracking createUpdatedEntity(EntityManager em) {
        PersonInteracitonTracking personInteracitonTracking = new PersonInteracitonTracking()
            .userId(UPDATED_USER_ID)
            .interactedUserId(UPDATED_INTERACTED_USER_ID)
            .locationLongitude(UPDATED_LOCATION_LONGITUDE)
            .locationLatitude(UPDATED_LOCATION_LATITUDE)
            .locationTime(UPDATED_LOCATION_TIME);
        return personInteracitonTracking;
    }

    @BeforeEach
    public void initTest() {
        personInteracitonTracking = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonInteracitonTracking() throws Exception {
        int databaseSizeBeforeCreate = personInteracitonTrackingRepository.findAll().size();

        // Create the PersonInteracitonTracking
        restPersonInteracitonTrackingMockMvc.perform(post("/api/person-interaciton-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personInteracitonTracking)))
            .andExpect(status().isCreated());

        // Validate the PersonInteracitonTracking in the database
        List<PersonInteracitonTracking> personInteracitonTrackingList = personInteracitonTrackingRepository.findAll();
        assertThat(personInteracitonTrackingList).hasSize(databaseSizeBeforeCreate + 1);
        PersonInteracitonTracking testPersonInteracitonTracking = personInteracitonTrackingList.get(personInteracitonTrackingList.size() - 1);
        assertThat(testPersonInteracitonTracking.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPersonInteracitonTracking.getInteractedUserId()).isEqualTo(DEFAULT_INTERACTED_USER_ID);
        assertThat(testPersonInteracitonTracking.getLocationLongitude()).isEqualTo(DEFAULT_LOCATION_LONGITUDE);
        assertThat(testPersonInteracitonTracking.getLocationLatitude()).isEqualTo(DEFAULT_LOCATION_LATITUDE);
        assertThat(testPersonInteracitonTracking.getLocationTime()).isEqualTo(DEFAULT_LOCATION_TIME);
    }

    @Test
    @Transactional
    public void createPersonInteracitonTrackingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personInteracitonTrackingRepository.findAll().size();

        // Create the PersonInteracitonTracking with an existing ID
        personInteracitonTracking.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonInteracitonTrackingMockMvc.perform(post("/api/person-interaciton-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personInteracitonTracking)))
            .andExpect(status().isBadRequest());

        // Validate the PersonInteracitonTracking in the database
        List<PersonInteracitonTracking> personInteracitonTrackingList = personInteracitonTrackingRepository.findAll();
        assertThat(personInteracitonTrackingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = personInteracitonTrackingRepository.findAll().size();
        // set the field null
        personInteracitonTracking.setUserId(null);

        // Create the PersonInteracitonTracking, which fails.

        restPersonInteracitonTrackingMockMvc.perform(post("/api/person-interaciton-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personInteracitonTracking)))
            .andExpect(status().isBadRequest());

        List<PersonInteracitonTracking> personInteracitonTrackingList = personInteracitonTrackingRepository.findAll();
        assertThat(personInteracitonTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInteractedUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = personInteracitonTrackingRepository.findAll().size();
        // set the field null
        personInteracitonTracking.setInteractedUserId(null);

        // Create the PersonInteracitonTracking, which fails.

        restPersonInteracitonTrackingMockMvc.perform(post("/api/person-interaciton-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personInteracitonTracking)))
            .andExpect(status().isBadRequest());

        List<PersonInteracitonTracking> personInteracitonTrackingList = personInteracitonTrackingRepository.findAll();
        assertThat(personInteracitonTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personInteracitonTrackingRepository.findAll().size();
        // set the field null
        personInteracitonTracking.setLocationLongitude(null);

        // Create the PersonInteracitonTracking, which fails.

        restPersonInteracitonTrackingMockMvc.perform(post("/api/person-interaciton-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personInteracitonTracking)))
            .andExpect(status().isBadRequest());

        List<PersonInteracitonTracking> personInteracitonTrackingList = personInteracitonTrackingRepository.findAll();
        assertThat(personInteracitonTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personInteracitonTrackingRepository.findAll().size();
        // set the field null
        personInteracitonTracking.setLocationLatitude(null);

        // Create the PersonInteracitonTracking, which fails.

        restPersonInteracitonTrackingMockMvc.perform(post("/api/person-interaciton-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personInteracitonTracking)))
            .andExpect(status().isBadRequest());

        List<PersonInteracitonTracking> personInteracitonTrackingList = personInteracitonTrackingRepository.findAll();
        assertThat(personInteracitonTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonInteracitonTrackings() throws Exception {
        // Initialize the database
        personInteracitonTrackingRepository.saveAndFlush(personInteracitonTracking);

        // Get all the personInteracitonTrackingList
        restPersonInteracitonTrackingMockMvc.perform(get("/api/person-interaciton-trackings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personInteracitonTracking.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].interactedUserId").value(hasItem(DEFAULT_INTERACTED_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].locationLongitude").value(hasItem(DEFAULT_LOCATION_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].locationLatitude").value(hasItem(DEFAULT_LOCATION_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].locationTime").value(hasItem(DEFAULT_LOCATION_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getPersonInteracitonTracking() throws Exception {
        // Initialize the database
        personInteracitonTrackingRepository.saveAndFlush(personInteracitonTracking);

        // Get the personInteracitonTracking
        restPersonInteracitonTrackingMockMvc.perform(get("/api/person-interaciton-trackings/{id}", personInteracitonTracking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personInteracitonTracking.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.interactedUserId").value(DEFAULT_INTERACTED_USER_ID.intValue()))
            .andExpect(jsonPath("$.locationLongitude").value(DEFAULT_LOCATION_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.locationLatitude").value(DEFAULT_LOCATION_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.locationTime").value(DEFAULT_LOCATION_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonInteracitonTracking() throws Exception {
        // Get the personInteracitonTracking
        restPersonInteracitonTrackingMockMvc.perform(get("/api/person-interaciton-trackings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonInteracitonTracking() throws Exception {
        // Initialize the database
        personInteracitonTrackingService.save(personInteracitonTracking);

        int databaseSizeBeforeUpdate = personInteracitonTrackingRepository.findAll().size();

        // Update the personInteracitonTracking
        PersonInteracitonTracking updatedPersonInteracitonTracking = personInteracitonTrackingRepository.findById(personInteracitonTracking.getId()).get();
        // Disconnect from session so that the updates on updatedPersonInteracitonTracking are not directly saved in db
        em.detach(updatedPersonInteracitonTracking);
        updatedPersonInteracitonTracking
            .userId(UPDATED_USER_ID)
            .interactedUserId(UPDATED_INTERACTED_USER_ID)
            .locationLongitude(UPDATED_LOCATION_LONGITUDE)
            .locationLatitude(UPDATED_LOCATION_LATITUDE)
            .locationTime(UPDATED_LOCATION_TIME);

        restPersonInteracitonTrackingMockMvc.perform(put("/api/person-interaciton-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonInteracitonTracking)))
            .andExpect(status().isOk());

        // Validate the PersonInteracitonTracking in the database
        List<PersonInteracitonTracking> personInteracitonTrackingList = personInteracitonTrackingRepository.findAll();
        assertThat(personInteracitonTrackingList).hasSize(databaseSizeBeforeUpdate);
        PersonInteracitonTracking testPersonInteracitonTracking = personInteracitonTrackingList.get(personInteracitonTrackingList.size() - 1);
        assertThat(testPersonInteracitonTracking.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPersonInteracitonTracking.getInteractedUserId()).isEqualTo(UPDATED_INTERACTED_USER_ID);
        assertThat(testPersonInteracitonTracking.getLocationLongitude()).isEqualTo(UPDATED_LOCATION_LONGITUDE);
        assertThat(testPersonInteracitonTracking.getLocationLatitude()).isEqualTo(UPDATED_LOCATION_LATITUDE);
        assertThat(testPersonInteracitonTracking.getLocationTime()).isEqualTo(UPDATED_LOCATION_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonInteracitonTracking() throws Exception {
        int databaseSizeBeforeUpdate = personInteracitonTrackingRepository.findAll().size();

        // Create the PersonInteracitonTracking

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonInteracitonTrackingMockMvc.perform(put("/api/person-interaciton-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personInteracitonTracking)))
            .andExpect(status().isBadRequest());

        // Validate the PersonInteracitonTracking in the database
        List<PersonInteracitonTracking> personInteracitonTrackingList = personInteracitonTrackingRepository.findAll();
        assertThat(personInteracitonTrackingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonInteracitonTracking() throws Exception {
        // Initialize the database
        personInteracitonTrackingService.save(personInteracitonTracking);

        int databaseSizeBeforeDelete = personInteracitonTrackingRepository.findAll().size();

        // Delete the personInteracitonTracking
        restPersonInteracitonTrackingMockMvc.perform(delete("/api/person-interaciton-trackings/{id}", personInteracitonTracking.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonInteracitonTracking> personInteracitonTrackingList = personInteracitonTrackingRepository.findAll();
        assertThat(personInteracitonTrackingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
