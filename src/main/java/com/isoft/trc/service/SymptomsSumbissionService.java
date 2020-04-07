package com.isoft.trc.service;

import com.isoft.trc.domain.SymptomsSumbission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SymptomsSumbission}.
 */
public interface SymptomsSumbissionService {

    /**
     * Save a symptomsSumbission.
     *
     * @param symptomsSumbission the entity to save.
     * @return the persisted entity.
     */
    SymptomsSumbission save(SymptomsSumbission symptomsSumbission);

    /**
     * Get all the symptomsSumbissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SymptomsSumbission> findAll(Pageable pageable);

    /**
     * Get the "id" symptomsSumbission.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SymptomsSumbission> findOne(Long id);

    /**
     * Delete the "id" symptomsSumbission.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
