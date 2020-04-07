package com.isoft.trc.service;

import com.isoft.trc.domain.PersonInteracitonTracking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link PersonInteracitonTracking}.
 */
public interface PersonInteracitonTrackingService {

    /**
     * Save a personInteracitonTracking.
     *
     * @param personInteracitonTracking the entity to save.
     * @return the persisted entity.
     */
    PersonInteracitonTracking save(PersonInteracitonTracking personInteracitonTracking);

    /**
     * Get all the personInteracitonTrackings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonInteracitonTracking> findAll(Pageable pageable);

    /**
     * Get the "id" personInteracitonTracking.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonInteracitonTracking> findOne(Long id);

    /**
     * Delete the "id" personInteracitonTracking.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
