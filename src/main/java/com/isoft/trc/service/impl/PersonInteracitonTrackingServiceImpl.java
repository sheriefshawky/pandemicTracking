package com.isoft.trc.service.impl;

import com.isoft.trc.service.PersonInteracitonTrackingService;
import com.isoft.trc.domain.PersonInteracitonTracking;
import com.isoft.trc.repository.PersonInteracitonTrackingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PersonInteracitonTracking}.
 */
@Service
@Transactional
public class PersonInteracitonTrackingServiceImpl implements PersonInteracitonTrackingService {

    private final Logger log = LoggerFactory.getLogger(PersonInteracitonTrackingServiceImpl.class);

    private final PersonInteracitonTrackingRepository personInteracitonTrackingRepository;

    public PersonInteracitonTrackingServiceImpl(PersonInteracitonTrackingRepository personInteracitonTrackingRepository) {
        this.personInteracitonTrackingRepository = personInteracitonTrackingRepository;
    }

    /**
     * Save a personInteracitonTracking.
     *
     * @param personInteracitonTracking the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PersonInteracitonTracking save(PersonInteracitonTracking personInteracitonTracking) {
        log.debug("Request to save PersonInteracitonTracking : {}", personInteracitonTracking);
        return personInteracitonTrackingRepository.save(personInteracitonTracking);
    }

    /**
     * Get all the personInteracitonTrackings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PersonInteracitonTracking> findAll(Pageable pageable) {
        log.debug("Request to get all PersonInteracitonTrackings");
        return personInteracitonTrackingRepository.findAll(pageable);
    }

    /**
     * Get one personInteracitonTracking by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonInteracitonTracking> findOne(Long id) {
        log.debug("Request to get PersonInteracitonTracking : {}", id);
        return personInteracitonTrackingRepository.findById(id);
    }

    /**
     * Delete the personInteracitonTracking by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonInteracitonTracking : {}", id);
        personInteracitonTrackingRepository.deleteById(id);
    }
}
