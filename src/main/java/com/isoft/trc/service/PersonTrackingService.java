package com.isoft.trc.service;

import com.isoft.trc.domain.PersonTracking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link PersonTracking}.
 */
public interface PersonTrackingService {

    /**
     * Save a personTracking.
     *
     * @param personTracking the entity to save.
     * @return the persisted entity.
     */
    PersonTracking save(PersonTracking personTracking);

    /**
     * Get all the personTrackings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonTracking> findAll(Pageable pageable);

    /**
     * Get the "id" personTracking.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonTracking> findOne(Long id);

    /**
     * Delete the "id" personTracking.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
