package com.isoft.trc.web.rest;

import com.isoft.trc.PandemicTrackingApp;
import com.isoft.trc.domain.SymptomsSumbission;
import com.isoft.trc.repository.SymptomsSumbissionRepository;
import com.isoft.trc.service.SymptomsSumbissionService;

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
 * Integration tests for the {@link SymptomsSumbissionResource} REST controller.
 */
@SpringBootTest(classes = PandemicTrackingApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SymptomsSumbissionResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Instant DEFAULT_SUBMISSION_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUBMISSION_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SymptomsSumbissionRepository symptomsSumbissionRepository;

    @Autowired
    private SymptomsSumbissionService symptomsSumbissionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSymptomsSumbissionMockMvc;

    private SymptomsSumbission symptomsSumbission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SymptomsSumbission createEntity(EntityManager em) {
        SymptomsSumbission symptomsSumbission = new SymptomsSumbission()
            .userId(DEFAULT_USER_ID)
            .submissionTime(DEFAULT_SUBMISSION_TIME);
        return symptomsSumbission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SymptomsSumbission createUpdatedEntity(EntityManager em) {
        SymptomsSumbission symptomsSumbission = new SymptomsSumbission()
            .userId(UPDATED_USER_ID)
            .submissionTime(UPDATED_SUBMISSION_TIME);
        return symptomsSumbission;
    }

    @BeforeEach
    public void initTest() {
        symptomsSumbission = createEntity(em);
    }

    @Test
    @Transactional
    public void createSymptomsSumbission() throws Exception {
        int databaseSizeBeforeCreate = symptomsSumbissionRepository.findAll().size();

        // Create the SymptomsSumbission
        restSymptomsSumbissionMockMvc.perform(post("/api/symptoms-sumbissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(symptomsSumbission)))
            .andExpect(status().isCreated());

        // Validate the SymptomsSumbission in the database
        List<SymptomsSumbission> symptomsSumbissionList = symptomsSumbissionRepository.findAll();
        assertThat(symptomsSumbissionList).hasSize(databaseSizeBeforeCreate + 1);
        SymptomsSumbission testSymptomsSumbission = symptomsSumbissionList.get(symptomsSumbissionList.size() - 1);
        assertThat(testSymptomsSumbission.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testSymptomsSumbission.getSubmissionTime()).isEqualTo(DEFAULT_SUBMISSION_TIME);
    }

    @Test
    @Transactional
    public void createSymptomsSumbissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = symptomsSumbissionRepository.findAll().size();

        // Create the SymptomsSumbission with an existing ID
        symptomsSumbission.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSymptomsSumbissionMockMvc.perform(post("/api/symptoms-sumbissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(symptomsSumbission)))
            .andExpect(status().isBadRequest());

        // Validate the SymptomsSumbission in the database
        List<SymptomsSumbission> symptomsSumbissionList = symptomsSumbissionRepository.findAll();
        assertThat(symptomsSumbissionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = symptomsSumbissionRepository.findAll().size();
        // set the field null
        symptomsSumbission.setUserId(null);

        // Create the SymptomsSumbission, which fails.

        restSymptomsSumbissionMockMvc.perform(post("/api/symptoms-sumbissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(symptomsSumbission)))
            .andExpect(status().isBadRequest());

        List<SymptomsSumbission> symptomsSumbissionList = symptomsSumbissionRepository.findAll();
        assertThat(symptomsSumbissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubmissionTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = symptomsSumbissionRepository.findAll().size();
        // set the field null
        symptomsSumbission.setSubmissionTime(null);

        // Create the SymptomsSumbission, which fails.

        restSymptomsSumbissionMockMvc.perform(post("/api/symptoms-sumbissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(symptomsSumbission)))
            .andExpect(status().isBadRequest());

        List<SymptomsSumbission> symptomsSumbissionList = symptomsSumbissionRepository.findAll();
        assertThat(symptomsSumbissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSymptomsSumbissions() throws Exception {
        // Initialize the database
        symptomsSumbissionRepository.saveAndFlush(symptomsSumbission);

        // Get all the symptomsSumbissionList
        restSymptomsSumbissionMockMvc.perform(get("/api/symptoms-sumbissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(symptomsSumbission.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].submissionTime").value(hasItem(DEFAULT_SUBMISSION_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getSymptomsSumbission() throws Exception {
        // Initialize the database
        symptomsSumbissionRepository.saveAndFlush(symptomsSumbission);

        // Get the symptomsSumbission
        restSymptomsSumbissionMockMvc.perform(get("/api/symptoms-sumbissions/{id}", symptomsSumbission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(symptomsSumbission.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.submissionTime").value(DEFAULT_SUBMISSION_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSymptomsSumbission() throws Exception {
        // Get the symptomsSumbission
        restSymptomsSumbissionMockMvc.perform(get("/api/symptoms-sumbissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSymptomsSumbission() throws Exception {
        // Initialize the database
        symptomsSumbissionService.save(symptomsSumbission);

        int databaseSizeBeforeUpdate = symptomsSumbissionRepository.findAll().size();

        // Update the symptomsSumbission
        SymptomsSumbission updatedSymptomsSumbission = symptomsSumbissionRepository.findById(symptomsSumbission.getId()).get();
        // Disconnect from session so that the updates on updatedSymptomsSumbission are not directly saved in db
        em.detach(updatedSymptomsSumbission);
        updatedSymptomsSumbission
            .userId(UPDATED_USER_ID)
            .submissionTime(UPDATED_SUBMISSION_TIME);

        restSymptomsSumbissionMockMvc.perform(put("/api/symptoms-sumbissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSymptomsSumbission)))
            .andExpect(status().isOk());

        // Validate the SymptomsSumbission in the database
        List<SymptomsSumbission> symptomsSumbissionList = symptomsSumbissionRepository.findAll();
        assertThat(symptomsSumbissionList).hasSize(databaseSizeBeforeUpdate);
        SymptomsSumbission testSymptomsSumbission = symptomsSumbissionList.get(symptomsSumbissionList.size() - 1);
        assertThat(testSymptomsSumbission.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testSymptomsSumbission.getSubmissionTime()).isEqualTo(UPDATED_SUBMISSION_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSymptomsSumbission() throws Exception {
        int databaseSizeBeforeUpdate = symptomsSumbissionRepository.findAll().size();

        // Create the SymptomsSumbission

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSymptomsSumbissionMockMvc.perform(put("/api/symptoms-sumbissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(symptomsSumbission)))
            .andExpect(status().isBadRequest());

        // Validate the SymptomsSumbission in the database
        List<SymptomsSumbission> symptomsSumbissionList = symptomsSumbissionRepository.findAll();
        assertThat(symptomsSumbissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSymptomsSumbission() throws Exception {
        // Initialize the database
        symptomsSumbissionService.save(symptomsSumbission);

        int databaseSizeBeforeDelete = symptomsSumbissionRepository.findAll().size();

        // Delete the symptomsSumbission
        restSymptomsSumbissionMockMvc.perform(delete("/api/symptoms-sumbissions/{id}", symptomsSumbission.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SymptomsSumbission> symptomsSumbissionList = symptomsSumbissionRepository.findAll();
        assertThat(symptomsSumbissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
