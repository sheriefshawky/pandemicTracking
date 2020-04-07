package com.isoft.trc.service;

import com.isoft.trc.domain.SymptomsSpecs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SymptomsSpecs}.
 */
public interface SymptomsSpecsService {

    /**
     * Save a symptomsSpecs.
     *
     * @param symptomsSpecs the entity to save.
     * @return the persisted entity.
     */
    SymptomsSpecs save(SymptomsSpecs symptomsSpecs);

    /**
     * Get all the symptomsSpecs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SymptomsSpecs> findAll(Pageable pageable);

    /**
     * Get the "id" symptomsSpecs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SymptomsSpecs> findOne(Long id);

    /**
     * Delete the "id" symptomsSpecs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
