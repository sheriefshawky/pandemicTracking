package com.isoft.trc.service.impl;

import com.isoft.trc.service.PersonTrackingService;
import com.isoft.trc.domain.PersonTracking;
import com.isoft.trc.repository.PersonTrackingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PersonTracking}.
 */
@Service
@Transactional
public class PersonTrackingServiceImpl implements PersonTrackingService {

    private final Logger log = LoggerFactory.getLogger(PersonTrackingServiceImpl.class);

    private final PersonTrackingRepository personTrackingRepository;

    public PersonTrackingServiceImpl(PersonTrackingRepository personTrackingRepository) {
        this.personTrackingRepository = personTrackingRepository;
    }

    /**
     * Save a personTracking.
     *
     * @param personTracking the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PersonTracking save(PersonTracking personTracking) {
        log.debug("Request to save PersonTracking : {}", personTracking);
        return personTrackingRepository.save(personTracking);
    }

    /**
     * Get all the personTrackings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PersonTracking> findAll(Pageable pageable) {
        log.debug("Request to get all PersonTrackings");
        return personTrackingRepository.findAll(pageable);
    }

    /**
     * Get one personTracking by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonTracking> findOne(Long id) {
        log.debug("Request to get PersonTracking : {}", id);
        return personTrackingRepository.findById(id);
    }

    /**
     * Delete the personTracking by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonTracking : {}", id);
        personTrackingRepository.deleteById(id);
    }
}
