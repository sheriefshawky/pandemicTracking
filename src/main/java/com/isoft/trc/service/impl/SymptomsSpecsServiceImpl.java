package com.isoft.trc.service.impl;

import com.isoft.trc.service.SymptomsSpecsService;
import com.isoft.trc.domain.SymptomsSpecs;
import com.isoft.trc.repository.SymptomsSpecsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SymptomsSpecs}.
 */
@Service
@Transactional
public class SymptomsSpecsServiceImpl implements SymptomsSpecsService {

    private final Logger log = LoggerFactory.getLogger(SymptomsSpecsServiceImpl.class);

    private final SymptomsSpecsRepository symptomsSpecsRepository;

    public SymptomsSpecsServiceImpl(SymptomsSpecsRepository symptomsSpecsRepository) {
        this.symptomsSpecsRepository = symptomsSpecsRepository;
    }

    /**
     * Save a symptomsSpecs.
     *
     * @param symptomsSpecs the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SymptomsSpecs save(SymptomsSpecs symptomsSpecs) {
        log.debug("Request to save SymptomsSpecs : {}", symptomsSpecs);
        return symptomsSpecsRepository.save(symptomsSpecs);
    }

    /**
     * Get all the symptomsSpecs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SymptomsSpecs> findAll(Pageable pageable) {
        log.debug("Request to get all SymptomsSpecs");
        return symptomsSpecsRepository.findAll(pageable);
    }

    /**
     * Get one symptomsSpecs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SymptomsSpecs> findOne(Long id) {
        log.debug("Request to get SymptomsSpecs : {}", id);
        return symptomsSpecsRepository.findById(id);
    }

    /**
     * Delete the symptomsSpecs by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SymptomsSpecs : {}", id);
        symptomsSpecsRepository.deleteById(id);
    }
}
