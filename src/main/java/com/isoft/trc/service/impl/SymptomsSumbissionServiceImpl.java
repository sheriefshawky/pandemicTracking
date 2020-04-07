package com.isoft.trc.service.impl;

import com.isoft.trc.service.SymptomsSumbissionService;
import com.isoft.trc.domain.SymptomsSumbission;
import com.isoft.trc.repository.SymptomsSumbissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SymptomsSumbission}.
 */
@Service
@Transactional
public class SymptomsSumbissionServiceImpl implements SymptomsSumbissionService {

    private final Logger log = LoggerFactory.getLogger(SymptomsSumbissionServiceImpl.class);

    private final SymptomsSumbissionRepository symptomsSumbissionRepository;

    public SymptomsSumbissionServiceImpl(SymptomsSumbissionRepository symptomsSumbissionRepository) {
        this.symptomsSumbissionRepository = symptomsSumbissionRepository;
    }

    /**
     * Save a symptomsSumbission.
     *
     * @param symptomsSumbission the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SymptomsSumbission save(SymptomsSumbission symptomsSumbission) {
        log.debug("Request to save SymptomsSumbission : {}", symptomsSumbission);
        return symptomsSumbissionRepository.save(symptomsSumbission);
    }

    /**
     * Get all the symptomsSumbissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SymptomsSumbission> findAll(Pageable pageable) {
        log.debug("Request to get all SymptomsSumbissions");
        return symptomsSumbissionRepository.findAll(pageable);
    }

    /**
     * Get one symptomsSumbission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SymptomsSumbission> findOne(Long id) {
        log.debug("Request to get SymptomsSumbission : {}", id);
        return symptomsSumbissionRepository.findById(id);
    }

    /**
     * Delete the symptomsSumbission by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SymptomsSumbission : {}", id);
        symptomsSumbissionRepository.deleteById(id);
    }
}
