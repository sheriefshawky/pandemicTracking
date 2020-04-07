package com.isoft.trc.web.rest;

import com.isoft.trc.PandemicTrackingApp;
import com.isoft.trc.domain.SymptomsSpecs;
import com.isoft.trc.repository.SymptomsSpecsRepository;
import com.isoft.trc.service.SymptomsSpecsService;

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
 * Integration tests for the {@link SymptomsSpecsResource} REST controller.
 */
@SpringBootTest(classes = PandemicTrackingApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SymptomsSpecsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_AR = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_AR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_EN = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_EN = "BBBBBBBBBB";

    private static final Integer DEFAULT_SPEC_TYPE = 1;
    private static final Integer UPDATED_SPEC_TYPE = 2;

    @Autowired
    private SymptomsSpecsRepository symptomsSpecsRepository;

    @Autowired
    private SymptomsSpecsService symptomsSpecsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSymptomsSpecsMockMvc;

    private SymptomsSpecs symptomsSpecs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SymptomsSpecs createEntity(EntityManager em) {
        SymptomsSpecs symptomsSpecs = new SymptomsSpecs()
            .code(DEFAULT_CODE)
            .descriptionAr(DEFAULT_DESCRIPTION_AR)
            .descriptionEn(DEFAULT_DESCRIPTION_EN)
            .specType(DEFAULT_SPEC_TYPE);
        return symptomsSpecs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SymptomsSpecs createUpdatedEntity(EntityManager em) {
        SymptomsSpecs symptomsSpecs = new SymptomsSpecs()
            .code(UPDATED_CODE)
            .descriptionAr(UPDATED_DESCRIPTION_AR)
            .descriptionEn(UPDATED_DESCRIPTION_EN)
            .specType(UPDATED_SPEC_TYPE);
        return symptomsSpecs;
    }

    @BeforeEach
    public void initTest() {
        symptomsSpecs = createEntity(em);
    }

    @Test
    @Transactional
    public void createSymptomsSpecs() throws Exception {
        int databaseSizeBeforeCreate = symptomsSpecsRepository.findAll().size();

        // Create the SymptomsSpecs
        restSymptomsSpecsMockMvc.perform(post("/api/symptoms-specs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(symptomsSpecs)))
            .andExpect(status().isCreated());

        // Validate the SymptomsSpecs in the database
        List<SymptomsSpecs> symptomsSpecsList = symptomsSpecsRepository.findAll();
        assertThat(symptomsSpecsList).hasSize(databaseSizeBeforeCreate + 1);
        SymptomsSpecs testSymptomsSpecs = symptomsSpecsList.get(symptomsSpecsList.size() - 1);
        assertThat(testSymptomsSpecs.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSymptomsSpecs.getDescriptionAr()).isEqualTo(DEFAULT_DESCRIPTION_AR);
        assertThat(testSymptomsSpecs.getDescriptionEn()).isEqualTo(DEFAULT_DESCRIPTION_EN);
        assertThat(testSymptomsSpecs.getSpecType()).isEqualTo(DEFAULT_SPEC_TYPE);
    }

    @Test
    @Transactional
    public void createSymptomsSpecsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = symptomsSpecsRepository.findAll().size();

        // Create the SymptomsSpecs with an existing ID
        symptomsSpecs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSymptomsSpecsMockMvc.perform(post("/api/symptoms-specs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(symptomsSpecs)))
            .andExpect(status().isBadRequest());

        // Validate the SymptomsSpecs in the database
        List<SymptomsSpecs> symptomsSpecsList = symptomsSpecsRepository.findAll();
        assertThat(symptomsSpecsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = symptomsSpecsRepository.findAll().size();
        // set the field null
        symptomsSpecs.setCode(null);

        // Create the SymptomsSpecs, which fails.

        restSymptomsSpecsMockMvc.perform(post("/api/symptoms-specs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(symptomsSpecs)))
            .andExpect(status().isBadRequest());

        List<SymptomsSpecs> symptomsSpecsList = symptomsSpecsRepository.findAll();
        assertThat(symptomsSpecsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionArIsRequired() throws Exception {
        int databaseSizeBeforeTest = symptomsSpecsRepository.findAll().size();
        // set the field null
        symptomsSpecs.setDescriptionAr(null);

        // Create the SymptomsSpecs, which fails.

        restSymptomsSpecsMockMvc.perform(post("/api/symptoms-specs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(symptomsSpecs)))
            .andExpect(status().isBadRequest());

        List<SymptomsSpecs> symptomsSpecsList = symptomsSpecsRepository.findAll();
        assertThat(symptomsSpecsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = symptomsSpecsRepository.findAll().size();
        // set the field null
        symptomsSpecs.setDescriptionEn(null);

        // Create the SymptomsSpecs, which fails.

        restSymptomsSpecsMockMvc.perform(post("/api/symptoms-specs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(symptomsSpecs)))
            .andExpect(status().isBadRequest());

        List<SymptomsSpecs> symptomsSpecsList = symptomsSpecsRepository.findAll();
        assertThat(symptomsSpecsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSpecTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = symptomsSpecsRepository.findAll().size();
        // set the field null
        symptomsSpecs.setSpecType(null);

        // Create the SymptomsSpecs, which fails.

        restSymptomsSpecsMockMvc.perform(post("/api/symptoms-specs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(symptomsSpecs)))
            .andExpect(status().isBadRequest());

        List<SymptomsSpecs> symptomsSpecsList = symptomsSpecsRepository.findAll();
        assertThat(symptomsSpecsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSymptomsSpecs() throws Exception {
        // Initialize the database
        symptomsSpecsRepository.saveAndFlush(symptomsSpecs);

        // Get all the symptomsSpecsList
        restSymptomsSpecsMockMvc.perform(get("/api/symptoms-specs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(symptomsSpecs.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].descriptionAr").value(hasItem(DEFAULT_DESCRIPTION_AR)))
            .andExpect(jsonPath("$.[*].descriptionEn").value(hasItem(DEFAULT_DESCRIPTION_EN)))
            .andExpect(jsonPath("$.[*].specType").value(hasItem(DEFAULT_SPEC_TYPE)));
    }
    
    @Test
    @Transactional
    public void getSymptomsSpecs() throws Exception {
        // Initialize the database
        symptomsSpecsRepository.saveAndFlush(symptomsSpecs);

        // Get the symptomsSpecs
        restSymptomsSpecsMockMvc.perform(get("/api/symptoms-specs/{id}", symptomsSpecs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(symptomsSpecs.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.descriptionAr").value(DEFAULT_DESCRIPTION_AR))
            .andExpect(jsonPath("$.descriptionEn").value(DEFAULT_DESCRIPTION_EN))
            .andExpect(jsonPath("$.specType").value(DEFAULT_SPEC_TYPE));
    }

    @Test
    @Transactional
    public void getNonExistingSymptomsSpecs() throws Exception {
        // Get the symptomsSpecs
        restSymptomsSpecsMockMvc.perform(get("/api/symptoms-specs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSymptomsSpecs() throws Exception {
        // Initialize the database
        symptomsSpecsService.save(symptomsSpecs);

        int databaseSizeBeforeUpdate = symptomsSpecsRepository.findAll().size();

        // Update the symptomsSpecs
        SymptomsSpecs updatedSymptomsSpecs = symptomsSpecsRepository.findById(symptomsSpecs.getId()).get();
        // Disconnect from session so that the updates on updatedSymptomsSpecs are not directly saved in db
        em.detach(updatedSymptomsSpecs);
        updatedSymptomsSpecs
            .code(UPDATED_CODE)
            .descriptionAr(UPDATED_DESCRIPTION_AR)
            .descriptionEn(UPDATED_DESCRIPTION_EN)
            .specType(UPDATED_SPEC_TYPE);

        restSymptomsSpecsMockMvc.perform(put("/api/symptoms-specs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSymptomsSpecs)))
            .andExpect(status().isOk());

        // Validate the SymptomsSpecs in the database
        List<SymptomsSpecs> symptomsSpecsList = symptomsSpecsRepository.findAll();
        assertThat(symptomsSpecsList).hasSize(databaseSizeBeforeUpdate);
        SymptomsSpecs testSymptomsSpecs = symptomsSpecsList.get(symptomsSpecsList.size() - 1);
        assertThat(testSymptomsSpecs.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSymptomsSpecs.getDescriptionAr()).isEqualTo(UPDATED_DESCRIPTION_AR);
        assertThat(testSymptomsSpecs.getDescriptionEn()).isEqualTo(UPDATED_DESCRIPTION_EN);
        assertThat(testSymptomsSpecs.getSpecType()).isEqualTo(UPDATED_SPEC_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSymptomsSpecs() throws Exception {
        int databaseSizeBeforeUpdate = symptomsSpecsRepository.findAll().size();

        // Create the SymptomsSpecs

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSymptomsSpecsMockMvc.perform(put("/api/symptoms-specs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(symptomsSpecs)))
            .andExpect(status().isBadRequest());

        // Validate the SymptomsSpecs in the database
        List<SymptomsSpecs> symptomsSpecsList = symptomsSpecsRepository.findAll();
        assertThat(symptomsSpecsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSymptomsSpecs() throws Exception {
        // Initialize the database
        symptomsSpecsService.save(symptomsSpecs);

        int databaseSizeBeforeDelete = symptomsSpecsRepository.findAll().size();

        // Delete the symptomsSpecs
        restSymptomsSpecsMockMvc.perform(delete("/api/symptoms-specs/{id}", symptomsSpecs.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SymptomsSpecs> symptomsSpecsList = symptomsSpecsRepository.findAll();
        assertThat(symptomsSpecsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
