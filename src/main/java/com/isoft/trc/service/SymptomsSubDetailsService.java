package com.isoft.trc.service;

import com.isoft.trc.domain.SymptomsSubDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SymptomsSubDetails}.
 */
public interface SymptomsSubDetailsService {

    /**
     * Save a symptomsSubDetails.
     *
     * @param symptomsSubDetails the entity to save.
     * @return the persisted entity.
     */
    SymptomsSubDetails save(SymptomsSubDetails symptomsSubDetails);

    /**
     * Get all the symptomsSubDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SymptomsSubDetails> findAll(Pageable pageable);

    /**
     * Get the "id" symptomsSubDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SymptomsSubDetails> findOne(Long id);

    /**
     * Delete the "id" symptomsSubDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
