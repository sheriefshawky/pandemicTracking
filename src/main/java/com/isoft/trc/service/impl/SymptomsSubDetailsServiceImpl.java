package com.isoft.trc.service.impl;

import com.isoft.trc.service.SymptomsSubDetailsService;
import com.isoft.trc.domain.SymptomsSubDetails;
import com.isoft.trc.repository.SymptomsSubDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SymptomsSubDetails}.
 */
@Service
@Transactional
public class SymptomsSubDetailsServiceImpl implements SymptomsSubDetailsService {

    private final Logger log = LoggerFactory.getLogger(SymptomsSubDetailsServiceImpl.class);

    private final SymptomsSubDetailsRepository symptomsSubDetailsRepository;

    public SymptomsSubDetailsServiceImpl(SymptomsSubDetailsRepository symptomsSubDetailsRepository) {
        this.symptomsSubDetailsRepository = symptomsSubDetailsRepository;
    }

    /**
     * Save a symptomsSubDetails.
     *
     * @param symptomsSubDetails the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SymptomsSubDetails save(SymptomsSubDetails symptomsSubDetails) {
        log.debug("Request to save SymptomsSubDetails : {}", symptomsSubDetails);
        return symptomsSubDetailsRepository.save(symptomsSubDetails);
    }

    /**
     * Get all the symptomsSubDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SymptomsSubDetails> findAll(Pageable pageable) {
        log.debug("Request to get all SymptomsSubDetails");
        return symptomsSubDetailsRepository.findAll(pageable);
    }

    /**
     * Get one symptomsSubDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SymptomsSubDetails> findOne(Long id) {
        log.debug("Request to get SymptomsSubDetails : {}", id);
        return symptomsSubDetailsRepository.findById(id);
    }

    /**
     * Delete the symptomsSubDetails by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SymptomsSubDetails : {}", id);
        symptomsSubDetailsRepository.deleteById(id);
    }
}
