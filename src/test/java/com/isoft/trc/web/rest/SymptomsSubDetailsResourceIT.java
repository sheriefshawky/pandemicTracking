package com.isoft.trc.web.rest;

import com.isoft.trc.PandemicTrackingApp;
import com.isoft.trc.domain.SymptomsSubDetails;
import com.isoft.trc.repository.SymptomsSubDetailsRepository;
import com.isoft.trc.service.SymptomsSubDetailsService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SymptomsSubDetailsResource} REST controller.
 */
@SpringBootTest(classes = PandemicTrackingApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SymptomsSubDetailsResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private SymptomsSubDetailsRepository symptomsSubDetailsRepository;

    @Autowired
    private SymptomsSubDetailsService symptomsSubDetailsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSymptomsSubDetailsMockMvc;

    private SymptomsSubDetails symptomsSubDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SymptomsSubDetails createEntity(EntityManager em) {
        SymptomsSubDetails symptomsSubDetails = new SymptomsSubDetails()
            .value(DEFAULT_VALUE);
        return symptomsSubDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SymptomsSubDetails createUpdatedEntity(EntityManager em) {
        SymptomsSubDetails symptomsSubDetails = new SymptomsSubDetails()
            .value(UPDATED_VALUE);
        return symptomsSubDetails;
    }

    @BeforeEach
    public void initTest() {
        symptomsSubDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createSymptomsSubDetails() throws Exception {
        int databaseSizeBeforeCreate = symptomsSubDetailsRepository.findAll().size();

        // Create the SymptomsSubDetails
        restSymptomsSubDetailsMockMvc.perform(post("/api/symptoms-sub-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(symptomsSubDetails)))
            .andExpect(status().isCreated());

        // Validate the SymptomsSubDetails in the database
        List<SymptomsSubDetails> symptomsSubDetailsList = symptomsSubDetailsRepository.findAll();
        assertThat(symptomsSubDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        SymptomsSubDetails testSymptomsSubDetails = symptomsSubDetailsList.get(symptomsSubDetailsList.size() - 1);
        assertThat(testSymptomsSubDetails.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createSymptomsSubDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = symptomsSubDetailsRepository.findAll().size();

        // Create the SymptomsSubDetails with an existing ID
        symptomsSubDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSymptomsSubDetailsMockMvc.perform(post("/api/symptoms-sub-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(symptomsSubDetails)))
            .andExpect(status().isBadRequest());

        // Validate the SymptomsSubDetails in the database
        List<SymptomsSubDetails> symptomsSubDetailsList = symptomsSubDetailsRepository.findAll();
        assertThat(symptomsSubDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = symptomsSubDetailsRepository.findAll().size();
        // set the field null
        symptomsSubDetails.setValue(null);

        // Create the SymptomsSubDetails, which fails.

        restSymptomsSubDetailsMockMvc.perform(post("/api/symptoms-sub-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(symptomsSubDetails)))
            .andExpect(status().isBadRequest());

        List<SymptomsSubDetails> symptomsSubDetailsList = symptomsSubDetailsRepository.findAll();
        assertThat(symptomsSubDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSymptomsSubDetails() throws Exception {
        // Initialize the database
        symptomsSubDetailsRepository.saveAndFlush(symptomsSubDetails);

        // Get all the symptomsSubDetailsList
        restSymptomsSubDetailsMockMvc.perform(get("/api/symptoms-sub-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(symptomsSubDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getSymptomsSubDetails() throws Exception {
        // Initialize the database
        symptomsSubDetailsRepository.saveAndFlush(symptomsSubDetails);

        // Get the symptomsSubDetails
        restSymptomsSubDetailsMockMvc.perform(get("/api/symptoms-sub-details/{id}", symptomsSubDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(symptomsSubDetails.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingSymptomsSubDetails() throws Exception {
        // Get the symptomsSubDetails
        restSymptomsSubDetailsMockMvc.perform(get("/api/symptoms-sub-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSymptomsSubDetails() throws Exception {
        // Initialize the database
        symptomsSubDetailsService.save(symptomsSubDetails);

        int databaseSizeBeforeUpdate = symptomsSubDetailsRepository.findAll().size();

        // Update the symptomsSubDetails
        SymptomsSubDetails updatedSymptomsSubDetails = symptomsSubDetailsRepository.findById(symptomsSubDetails.getId()).get();
        // Disconnect from session so that the updates on updatedSymptomsSubDetails are not directly saved in db
        em.detach(updatedSymptomsSubDetails);
        updatedSymptomsSubDetails
            .value(UPDATED_VALUE);

        restSymptomsSubDetailsMockMvc.perform(put("/api/symptoms-sub-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSymptomsSubDetails)))
            .andExpect(status().isOk());

        // Validate the SymptomsSubDetails in the database
        List<SymptomsSubDetails> symptomsSubDetailsList = symptomsSubDetailsRepository.findAll();
        assertThat(symptomsSubDetailsList).hasSize(databaseSizeBeforeUpdate);
        SymptomsSubDetails testSymptomsSubDetails = symptomsSubDetailsList.get(symptomsSubDetailsList.size() - 1);
        assertThat(testSymptomsSubDetails.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingSymptomsSubDetails() throws Exception {
        int databaseSizeBeforeUpdate = symptomsSubDetailsRepository.findAll().size();

        // Create the SymptomsSubDetails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSymptomsSubDetailsMockMvc.perform(put("/api/symptoms-sub-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(symptomsSubDetails)))
            .andExpect(status().isBadRequest());

        // Validate the SymptomsSubDetails in the database
        List<SymptomsSubDetails> symptomsSubDetailsList = symptomsSubDetailsRepository.findAll();
        assertThat(symptomsSubDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSymptomsSubDetails() throws Exception {
        // Initialize the database
        symptomsSubDetailsService.save(symptomsSubDetails);

        int databaseSizeBeforeDelete = symptomsSubDetailsRepository.findAll().size();

        // Delete the symptomsSubDetails
        restSymptomsSubDetailsMockMvc.perform(delete("/api/symptoms-sub-details/{id}", symptomsSubDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SymptomsSubDetails> symptomsSubDetailsList = symptomsSubDetailsRepository.findAll();
        assertThat(symptomsSubDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
